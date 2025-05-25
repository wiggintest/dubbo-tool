package com.wiggin.dubbotool.domain.model;

import com.intellij.psi.PsiClass;

import java.util.List;

/**
 * @description: 字段信息
 * @author: wiggin
 * @date: 2024-09-21 21:20
 **/
public class FieldInfoDO {

    /**
     * 是否基础类型
     */
    private Boolean basicType;

    private PsiClass psiClass;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段类型
     */
    private String fieldType;

    /**
     * 值
     */
    private Object value;

    /**
     * 字段为类的时候，该类包含的字段信息
     */
    private List<FieldInfoDO> fieldInfoDOList;

    public Boolean getBasicType() {
        return basicType;
    }

    public void setBasicType(Boolean basicType) {
        this.basicType = basicType;
    }

    public PsiClass getPsiClass() {
        return psiClass;
    }

    public void setPsiClass(PsiClass psiClass) {
        this.psiClass = psiClass;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public List<FieldInfoDO> getFieldInfoDOList() {
        return fieldInfoDOList;
    }

    public void setFieldInfoDOList(List<FieldInfoDO> fieldInfoDOList) {
        this.fieldInfoDOList = fieldInfoDOList;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
