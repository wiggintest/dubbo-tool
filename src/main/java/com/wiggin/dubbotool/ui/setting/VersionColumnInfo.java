package com.wiggin.dubbotool.ui.setting;

import com.intellij.util.ui.table.TableModelEditor;
import com.wiggin.dubbotool.domain.model.DubboToolSetting;
import org.jetbrains.annotations.Nullable;

import static com.wiggin.dubbotool.common.constant.DubboSettingConstant.VERSION_COLUMN;

/**
 * @description: name
 * @author: wiggin
 * @date: 2024-11-01 23:27
 **/
public class VersionColumnInfo extends TableModelEditor.EditableColumnInfo<DubboToolSetting,String> {

    public VersionColumnInfo() {
        super(VERSION_COLUMN);
    }

    @Override
    public @Nullable String valueOf(DubboToolSetting dubboToolSetting) {
        return dubboToolSetting.getVersion();
    }

    @Override
    public boolean isCellEditable(DubboToolSetting dubboToolSetting) {
        return false;
    }
}
