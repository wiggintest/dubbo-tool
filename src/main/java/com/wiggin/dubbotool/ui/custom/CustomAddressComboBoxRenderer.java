package com.wiggin.dubbotool.ui.custom;

import com.wiggin.dubbotool.domain.model.DubboToolSetting;

import javax.swing.*;
import java.awt.*;

/**
 * @description: 地址下拉渲染器
 * @author: wiggin
 * @date: 2024-11-03 22:23
 **/
public class CustomAddressComboBoxRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof DubboToolSetting item) {
            // 设置下拉展示的内容
            value = item.getName();
        }
        return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
    }
}
