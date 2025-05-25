package com.wiggin.dubbotool.domain.parser;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiClassType;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.InheritanceUtil;
import com.intellij.psi.util.PsiTypesUtil;
import com.intellij.psi.util.PsiUtil;
import com.wiggin.dubbotool.domain.model.ClazzInfoDTO;
import com.wiggin.dubbotool.domain.model.DubboToolContext;
import com.wiggin.dubbotool.domain.parser.support.ValueSupport;
import com.wiggin.dubbotool.domain.parser.support.ValueSupportFactory;

import java.util.*;

/**
 * @description: 解析器抽象
 * @author: wiggin
 * @date: 2024-09-22 10:45
 **/
public abstract class AbstractParser implements Parser {

    private static Set<String> WRAPPER_TYPES = new HashSet<>();

    /**
     * 数值类型的父类都是Number
     * 字符串类型的父类都是CharSequence
     * 基础类型中只有bool需要单独拎出来
     */
    static {
        WRAPPER_TYPES.add(Boolean.class.getCanonicalName());
        WRAPPER_TYPES.add(Number.class.getCanonicalName());
        WRAPPER_TYPES.add(CharSequence.class.getCanonicalName());
    }

    protected DubboToolContext dubboToolContext;
    private Parser next;

    public AbstractParser(DubboToolContext dubboToolContext) {
        this.dubboToolContext = dubboToolContext;
    }

    public void setNext(Parser next) {
        this.next = next;
    }

    @Override
    public void handle(ClazzInfoDTO clazzInfoDTO) {
        this.doParser(clazzInfoDTO);
        if (next != null) {
            next.handle(clazzInfoDTO);
        }
    }

    protected abstract void doParser(ClazzInfoDTO clazzInfoDTO);


    public boolean isCollectionType(PsiType psiType) {
        if (!(psiType instanceof PsiClassType psiClassType)) {
            return false;
        }
        PsiClass psiClass = PsiUtil.resolveClassInClassTypeOnly(psiType);
        return InheritanceUtil.isInheritor(psiClass, Collection.class.getCanonicalName());
    }

    public PsiType getCollectionGenericType(PsiType psiType) {
        if (!(psiType instanceof PsiClassType)) {
            return null;  // 不是类类型，返回null
        }

        PsiClassType psiClassType = (PsiClassType) psiType;
        PsiType[] parameters = psiClassType.getParameters();

        // 检查是否有泛型参数
        if (parameters.length == 0) {
            return null;
        }

        // 返回第一个泛型参数，针对List来说，它通常只有一个泛型参数
        return parameters[0];
    }

    protected Object getValue(PsiType psiType) {
        // list 单独处理
        if (isCollectionType(psiType)) {
            return getCollectionValue(psiType);
        }
        ValueSupport valueSupport = ValueSupportFactory.getValueSupport(psiType.getCanonicalText());
        if (Objects.nonNull(valueSupport)) {
            return valueSupport.getValue();
        }
        // 看父类是否支持
        PsiClass psiClass = PsiTypesUtil.getPsiClass(psiType);
        PsiClassType[] superTypes = psiClass.getSuperTypes();
        for (PsiClassType superType : superTypes) {
            valueSupport = ValueSupportFactory.getValueSupport(superType.getCanonicalText());
            if (Objects.nonNull(valueSupport)) {
                return valueSupport.getValue();
            }
        }
        return null;
    }

    /**
     * 获取集合类型的值
     *
     * @param psiType
     * @return
     */
    private Object getCollectionValue(PsiType psiType) {
        PsiType collectionGenericType = getCollectionGenericType(psiType);
        // 嵌套的泛型 List<List<String>>
        if (isCollectionType(collectionGenericType)) {
            return List.of(getCollectionValue(collectionGenericType));
        }
        Object value = getValue(collectionGenericType);
        if (Objects.nonNull(value)) {
            return List.of(value);
        }
        PsiClass psiClass = PsiUtil.resolveClassInType(collectionGenericType);
        PsiField[] fields = psiClass.getFields();
        HashMap<String, Object> map = new HashMap<>();
        for (PsiField field : fields) {
            PsiType fieldType = field.getType();
            map.put(field.getName(), getValue(fieldType));
        }
        return List.of(map);
    }


    /**
     * 获取类型，不包含泛型
     *
     * @param type
     * @return
     */
    protected String getTypeWithoutGenerics(PsiType type) {
        if (type instanceof PsiClassType) {
            PsiClass psiClass = ((PsiClassType) type).resolve();
            if (psiClass != null) {
                return psiClass.getQualifiedName(); // 或使用 getName() 获取不带包名的名称
            }
        }
        return type.getCanonicalText(); // 如果不是泛型类型，返回其规范文本
    }


    protected PsiType getPsiType(PsiType psiType) {
        if (psiType instanceof PsiClassType) {
            PsiClassType psiClassType = (PsiClassType) psiType;
            return psiClassType.rawType();
        }
        return psiType;
    }
}
