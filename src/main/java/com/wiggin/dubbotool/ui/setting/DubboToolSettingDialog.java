package com.wiggin.dubbotool.ui.setting;

import com.intellij.openapi.ui.ComboBox;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import com.wiggin.dubbotool.common.enums.AddressTypeEnum;
import com.wiggin.dubbotool.domain.model.DubboToolSetting;

import javax.swing.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.UUID;

/**
 * @description: dubbo配置弹框
 * @author: wiggin
 * @date: 2024-11-02 10:47
 **/
public class DubboToolSettingDialog {

    private JPanel panel;
    private JComboBox<String> comboBox;
    private JBTextField nameField;
    private JBTextField ipField;
    private JBTextField portField;
    private JBTextField versionField;
    private JBTextField groupField;

    public DubboToolSettingDialog(DubboToolSetting dubboToolSetting) {
        this.comboBox = this.getComboBox(dubboToolSetting);
        this.nameField = new JBTextField(dubboToolSetting.getName());
        this.ipField = new JBTextField();
        this.portField = new JBTextField();
        if (Objects.nonNull(dubboToolSetting.getAddress())) {
            try {
                URI  uri = new URI(dubboToolSetting.getAddress());
                ipField.setText(uri.getHost());
                portField.setText(uri.getPort() + "");
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        this.versionField = new JBTextField(dubboToolSetting.getVersion());
        this.groupField = new JBTextField(dubboToolSetting.getGroup());
        this.panel = FormBuilder.createFormBuilder()
                .addLabeledComponent("Name", nameField)
                .addLabeledComponent("Type", comboBox)
                .addLabeledComponent("IP", ipField)
                .addLabeledComponent("Port", portField)
                .addLabeledComponent("Version", versionField)
                .addLabeledComponent("Group", groupField)
                .getPanel();
    }

    private JComboBox<String> getComboBox(DubboToolSetting dubboToolSetting) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setEditable(false);
        // 添加选项
        AddressTypeEnum[] values = AddressTypeEnum.values();
        for (AddressTypeEnum value : values) {
            comboBox.addItem(value.getType());
        }
        // 默认选中
        comboBox.setSelectedItem(AddressTypeEnum.DUBBO.getType());
        String address = dubboToolSetting.getAddress();
        if ( Objects.nonNull(address) && !address.isEmpty()) {
            for (AddressTypeEnum value : values) {
                if (address.startsWith(value.getPrefix())) {
                    comboBox.setSelectedItem(value.getType());
                }
            }
        }
        return comboBox;
    }


    public JPanel getPanel() {
        return panel;
    }

    public DubboToolSetting getDubboToolSetting() {
        DubboToolSetting toolSetting = new DubboToolSetting();
        toolSetting.setName(nameField.getText());
        toolSetting.setVersion(versionField.getText());
        toolSetting.setGroup(groupField.getText());
        String selectedItem = (String) comboBox.getSelectedItem();
        AddressTypeEnum addressTypeEnum = AddressTypeEnum.getByType(selectedItem);
        String address = addressTypeEnum.getPrefix() + ipField.getText() + ":" + portField.getText();
        toolSetting.setAddress(address);
        toolSetting.setId(UUID.randomUUID().toString());
        return toolSetting;
    }
}
