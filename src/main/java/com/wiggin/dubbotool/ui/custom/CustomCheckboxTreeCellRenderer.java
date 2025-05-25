package com.wiggin.dubbotool.ui.custom;

import com.intellij.ui.CheckboxTree;
import com.intellij.ui.SimpleTextAttributes;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @description: 自定义checkbox tree
 * @author: wiggin
 * @date: 2024-09-28 17:31
 **/
public class CustomCheckboxTreeCellRenderer extends CheckboxTree.CheckboxTreeCellRenderer {
    @Override
    public void customizeRenderer(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (value instanceof DefaultMutableTreeNode node) {
            // 自定义渲染文本
            getTextRenderer().append(node.getUserObject().toString(), SimpleTextAttributes.REGULAR_ATTRIBUTES);
        }
    }
}
