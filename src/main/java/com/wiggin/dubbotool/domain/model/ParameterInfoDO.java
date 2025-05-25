package com.wiggin.dubbotool.domain.model;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiType;

import java.util.List;

/**
 * @description: 参数信息
 * @author: wiggin
 * @date: 2024-08-29 19:05
 **/
public class ParameterInfoDO {


    /**
     * 是否基础类型
     */
    private Boolean basicType;

    /**
     * 值
     */
    private Object value;

    /**
     * 参数名称
     */
    private String parameterName;

    /**
     * 参数类型
     */
    private String parameterType;

    /**
     * psiType
     */
    private PsiType psiType;

    /**
     * 参数类型psi
     */
    private PsiClass psiClass;

    private List<FieldInfoDO> fieldInfoDOList;


    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public PsiClass getPsiClass() {
        return psiClass;
    }

    public void setPsiClass(PsiClass psiClass) {
        this.psiClass = psiClass;
    }


    public Boolean getBasicType() {
        return basicType;
    }

    public void setBasicType(Boolean basicType) {
        this.basicType = basicType;
    }


    public List<FieldInfoDO> getFieldInfoDOList() {
        return fieldInfoDOList;
    }

    public void setFieldInfoDOList(List<FieldInfoDO> fieldInfoDOList) {
        this.fieldInfoDOList = fieldInfoDOList;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public PsiType getPsiType() {
        return psiType;
    }

    public void setPsiType(PsiType psiType) {
        this.psiType = psiType;
    }
}
