package com.wiggin.dubbotool.domain.parser;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiType;
import com.intellij.psi.util.InheritanceUtil;
import com.intellij.psi.util.PsiTypesUtil;
import com.wiggin.dubbotool.domain.model.ClazzInfoDTO;
import com.wiggin.dubbotool.domain.model.DubboToolContext;
import com.wiggin.dubbotool.domain.model.FieldInfoDO;
import com.wiggin.dubbotool.domain.model.ParameterInfoDO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @description: 字段解析
 * @author: wiggin
 * @date: 2024-09-23 21:21
 **/
public class FieldParser extends AbstractParser {

    /**
     * 最大递归深度
     */
    public static final int MAX_DEPTH = 5;

    public FieldParser(DubboToolContext dubboToolContext) {
        super(dubboToolContext);
    }

    @Override
    protected void doParser(ClazzInfoDTO clazzInfoDTO) {
        List<ParameterInfoDO> parameterInfoDOList = clazzInfoDTO.getParameterInfoDOList();
        for (ParameterInfoDO parameterInfoDO : parameterInfoDOList) {
            // map类型处理
            PsiClass psiClass = parameterInfoDO.getPsiClass();
            // list类型处理
            if (InheritanceUtil.isInheritor(psiClass, Collection.class.getCanonicalName())) {
                this.handleCollection(psiClass, parameterInfoDO);
                continue;
            }
            // 已经解析过的不做处理
            if (Objects.nonNull(parameterInfoDO.getValue())) {
                continue;
            }
            List<FieldInfoDO> fieldInfoDOList = this.parsePsiFields(psiClass, 0);
            parameterInfoDO.setFieldInfoDOList(fieldInfoDOList);
        }
    }

    /**
     * 处理collection类型，取其子类
     *
     * @param psiClass
     * @param parameterInfoDO
     */
    private void handleCollection(PsiClass psiClass, ParameterInfoDO parameterInfoDO) {

    }


    private List<FieldInfoDO> parsePsiFields(PsiClass psiClass, int currentDepth) {
        if (currentDepth >= MAX_DEPTH) {
            return List.of();
        }
        currentDepth++;
        List<FieldInfoDO> fieldInfoDOList = new ArrayList<>();
        PsiField[] fields = psiClass.getFields();
        for (PsiField field : fields) {
            // 忽略序列化字段：serialVersionUID 和 transient 字段
            if (isSerializationField(field)) {
                continue;
            }
            PsiType sourceType = field.getType();
            PsiType fieldType = getPsiType(field.getType());
            FieldInfoDO fieldInfoDO = new FieldInfoDO();
            fieldInfoDOList.add(fieldInfoDO);
            fieldInfoDO.setFieldName(field.getName());
            fieldInfoDO.setFieldType(fieldType.getCanonicalText());
            Object value = super.getValue(sourceType);
            if (Objects.nonNull(value)) {
                fieldInfoDO.setValue(value);
                continue;
            }
            PsiClass fieldPsiClass = PsiTypesUtil.getPsiClass(fieldType);
            // 递归处理
            fieldInfoDO.setPsiClass(fieldPsiClass);
            fieldInfoDO.setFieldInfoDOList(this.parsePsiFields(fieldPsiClass, currentDepth));
        }
        return fieldInfoDOList;
    }

    /**
     * 判断是否为序列化相关字段
     *
     * @param field 字段
     * @return 是否为序列化字段
     */
    private boolean isSerializationField(PsiField field) {
        // 忽略 serialVersionUID
        if ("serialVersionUID".equals(field.getName())) {
            return true;
        }
        // 忽略 transient 字段
        return field.getModifierList() != null && field.getModifierList().hasModifierProperty("transient");
    }
}
