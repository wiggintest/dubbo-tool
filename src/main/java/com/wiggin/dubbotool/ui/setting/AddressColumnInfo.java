package com.wiggin.dubbotool.ui.setting;

import com.intellij.util.ui.table.TableModelEditor;
import com.wiggin.dubbotool.domain.model.DubboToolSetting;
import org.jetbrains.annotations.Nullable;

import static com.wiggin.dubbotool.common.constant.DubboSettingConstant.ADDRESS_COLUMN;

/**
 * @description: name
 * @author: wiggin
 * @date: 2024-11-01 23:27
 **/
public class AddressColumnInfo extends TableModelEditor.EditableColumnInfo<DubboToolSetting,String> {

    public AddressColumnInfo() {
        super(ADDRESS_COLUMN);
    }

    @Override
    public @Nullable String valueOf(DubboToolSetting dubboToolSetting) {
        return dubboToolSetting.getAddress();
    }

    @Override
    public boolean isCellEditable(DubboToolSetting dubboToolSetting) {
        return false;
    }
}
