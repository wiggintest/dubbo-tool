package com.wiggin.dubbotool.util;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @description: 插件工具类，用于通过idea提供一些工具方法，获取类信息等
 * @author: wiggin
 * @date: 2024-08-27 11:12
 **/
public class PluginUtil {

    /**
     * 是否是合法的element
     *
     * @param element
     */
    public static boolean isLegalElement(PsiElement element) {
        return element instanceof PsiMethod;
    }

    /**
     * 获取类名
     *
     * @param element
     * @return
     */
    public static String getClassName(PsiElement element) {
        PsiClass psi = PsiTreeUtil.getParentOfType(element, PsiClass.class);
        if (Objects.nonNull(psi)) {
            return psi.getQualifiedName();
        }
        return null;
    }

    /**
     * 获取方法名
     *
     * @param element
     * @return
     */
    public static String getMethodName(PsiElement element) {
        return ((PsiMethod) element).getName();
    }

    public static Pair<List<String>, List<PsiType>> getParameterType(PsiElement element) {
        PsiMethod method = (PsiMethod) element;
        PsiParameterList parameterList = method.getParameterList();
        PsiParameter[] parameters = parameterList.getParameters();
        ArrayList<PsiType> parameterTypes = new ArrayList<>();
        ArrayList<String> parameterNames = new ArrayList<>();
        for (PsiParameter parameter : parameters) {
            parameterTypes.add(parameter.getType());
            parameterNames.add(parameter.getName());
        }
        return Pair.of(parameterNames, parameterTypes);
    }

}
