package com.wiggin.dubbotool.common.enums;

/**
 * @description: 类型
 * @author: wiggin
 * @date: 2024-11-12 22:02
 **/
public enum CollectionTypeEnum {
    /**
     * 收藏
     */
    FAVORITES("favorites"),

    /**
     * 历史
     */
    HISTORY("history");
    ;

    private final String type;

    CollectionTypeEnum(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
