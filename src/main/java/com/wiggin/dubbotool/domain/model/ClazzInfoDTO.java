package com.wiggin.dubbotool.domain.model;

import java.util.List;

/**
 * @description: psiClass 信息
 * @author: wiggin
 * @date: 2024-08-30 11:06
 **/
public class ClazzInfoDTO {

    private String clazzName;

    private String methodName;

    private List<ParameterInfoDO> parameterInfoDOList;

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public List<ParameterInfoDO> getParameterInfoDOList() {
        return parameterInfoDOList;
    }

    public void setParameterInfoDOList(List<ParameterInfoDO> parameterInfoDOList) {
        this.parameterInfoDOList = parameterInfoDOList;
    }

    @Override
    public String toString() {
        return "PsiClassInfoDTO{" +
                "clazzName='" + clazzName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameterInfoDOList=" + parameterInfoDOList +
                '}';
    }
}
