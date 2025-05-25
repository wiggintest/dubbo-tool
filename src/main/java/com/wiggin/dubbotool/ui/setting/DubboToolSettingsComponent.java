package com.wiggin.dubbotool.ui.setting;

import com.intellij.util.ui.ColumnInfo;
import com.intellij.util.ui.table.TableModelEditor;
import com.wiggin.dubbotool.domain.model.DubboToolSetting;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @description: 配置界面
 * @author: wiggin
 * @date: 2024-11-01 23:39
 **/
public class DubboToolSettingsComponent {
    private JComponent browsersTable;

    private TableModelEditor<DubboToolSetting> dubboToolSettingEditor;

    private static final ColumnInfo[] COLUMNS = {new NameColumnInfo(), new AddressColumnInfo(), new GroupColumnInfo(), new VersionColumnInfo()};

    public DubboToolSettingsComponent() {
        browsersTable = new JPanel();
        DubboToolDialogItemEditor dubboToolDialogItemEditor = new DubboToolDialogItemEditor(this);

        dubboToolSettingEditor = new TableModelEditor<>(COLUMNS,
                dubboToolDialogItemEditor,
                "No dubbo configured");
        browsersTable.setLayout(new BorderLayout());
        browsersTable.add(dubboToolSettingEditor.createComponent(), BorderLayout.CENTER);
    }

    public JComponent getComponent() {
        return browsersTable;
    }

    public JComponent getBrowsersTable() {
        return browsersTable;
    }

    public List<DubboToolSetting> getAllSettings() {
        return dubboToolSettingEditor.apply();
    }

    /**
     * 配置是否有变更
     *
     * @return
     */
    public boolean isModified() {
        return this.dubboToolSettingEditor.isModified();
    }

    public void reset(List<DubboToolSetting> dubboToolSettings) {
        this.dubboToolSettingEditor.reset(dubboToolSettings);
    }
}
