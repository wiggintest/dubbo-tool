package com.wiggin.dubbotool.ui.custom;

import com.intellij.ui.CheckedTreeNode;

/**
 * @description: 自定义checkNode
 * @author: wiggin
 * @date: 2024-09-28 23:05
 **/
public class CustomCheckedTreeNode extends CheckedTreeNode {

    private String className;
    private Object value;


    public CustomCheckedTreeNode(Object userObject,Object value,String className) {
        super(userObject);
        this.value = value;
        this.className = className;
    }

    public Object getValue() {
        return value;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassName() {
        return className;
    }
}
