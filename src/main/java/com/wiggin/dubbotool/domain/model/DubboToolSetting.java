package com.wiggin.dubbotool.domain.model;

import java.io.Serializable;

/**
 * @description: dubbo工具配置
 * @author: wiggin
 * @date: 2024-10-31 22:28
 **/
public class DubboToolSetting implements Serializable {

    private String id;
    private String name;
    private String version;
    private String group;
    private String address;

    public DubboToolSetting() {
    }

    public DubboToolSetting(String id, String name, String version, String group, String address) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.group = group;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return address;
    }
}
