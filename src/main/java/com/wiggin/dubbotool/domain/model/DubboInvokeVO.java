package com.wiggin.dubbotool.domain.model;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @description: 发起dubbo调用所需的参数
 * @author: wiggin
 * @date: 2024-08-29 17:23
 **/
public class DubboInvokeVO implements Serializable {

    private String serviceName;

    private String methodName;

    private String groupName;

    private String version;

    private String[] parameterTypes;

    private Object[] param;

    private String address;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(String[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParam() {
        return param;
    }

    public void setParam(Object[] param) {
        this.param = param;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return "DubboInvokeVO{" +
                "serviceName='" + serviceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", version='" + version + '\'' +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", param=" + Arrays.toString(param) +
                ", address='" + address + '\'' +
                '}';
    }
}
