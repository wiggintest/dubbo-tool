package com.wiggin.dubbotool.ui.setting;

import com.intellij.util.ui.table.TableModelEditor;
import com.wiggin.dubbotool.domain.model.DubboToolSetting;
import org.jetbrains.annotations.Nullable;

import static com.wiggin.dubbotool.common.constant.DubboSettingConstant.GROUP_COLUMN;

/**
 * @description: name
 * @author: wiggin
 * @date: 2024-11-01 23:27
 **/
public class GroupColumnInfo extends TableModelEditor.EditableColumnInfo<DubboToolSetting,String> {

    public GroupColumnInfo() {
        super(GROUP_COLUMN);
    }

    @Override
    public @Nullable String valueOf(DubboToolSetting dubboToolSetting) {
        return dubboToolSetting.getGroup();
    }

    @Override
    public boolean isCellEditable(DubboToolSetting dubboToolSetting) {
        return false;
    }
}
