package com.wiggin.dubbotool.common.enums;

/**
 * @description: 类型
 * @author: wiggin
 * @date: 2024-11-02 10:52
 **/
public enum AddressTypeEnum {
    /**
     * dubbo直连
     */
    DUBBO("dubbo", "dubbo://"),
    ZOOKEEPER("zookeeper", "zookeeper://");;

    /**
     * 类型
     */
    private String type;

    /**
     * 前缀
     */
    private String prefix;

    AddressTypeEnum(String type, String prefix) {
        this.type = type;
        this.prefix = prefix;
    }


    public static AddressTypeEnum getByType(String type) {
        for (AddressTypeEnum addressTypeEnum : AddressTypeEnum.values()) {
            if (addressTypeEnum.getType().equals(type)) {
                return addressTypeEnum;
            }
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public String getPrefix() {
        return prefix;
    }


}
