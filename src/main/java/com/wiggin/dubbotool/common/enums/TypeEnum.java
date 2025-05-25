package com.wiggin.dubbotool.common.enums;

public enum TypeEnum {

    /**
     * 基础类型
     */
    PRIMITIVE_TYPE(1),
    /**
     * 包装类型
     */
    WRAPPER_TYPE(2),
    /**
     * 集合类型
     */
    ITERABLE_TYPE(3),
    /**
     * 类类型
     */
    CLAZZ_TYPE(4);

    private Integer type;

    private TypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }


}
