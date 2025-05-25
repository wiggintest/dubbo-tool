package com.wiggin.dubbotool.ui.setting;

import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.util.Function;
import com.intellij.util.ui.table.TableModelEditor;
import com.wiggin.dubbotool.domain.model.DubboToolSetting;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.List;
import java.util.UUID;

/**
 * @description:
 * @author: wiggin
 * @date: 2024-11-01 23:43
 **/
public class DubboToolDialogItemEditor implements TableModelEditor.DialogItemEditor<DubboToolSetting> {

    private final DubboToolSettingsComponent dubboToolSettingsComponent;

    public DubboToolDialogItemEditor(DubboToolSettingsComponent dubboToolSettingsComponent) {
        this.dubboToolSettingsComponent = dubboToolSettingsComponent;
    }

    @Override
    public void edit(@NotNull DubboToolSetting dubboToolSetting, @NotNull Function<? super DubboToolSetting, ? extends DubboToolSetting> function, boolean b) {
        DubboToolSetting toolSetting = this.openDialog(dubboToolSetting);
        DubboToolSetting fun = function.fun(dubboToolSetting);

        if (toolSetting != null) {
            fun.setId(toolSetting.getId());
            fun.setName(toolSetting.getName());
            fun.setVersion(toolSetting.getVersion());
            fun.setGroup(toolSetting.getGroup());
            fun.setAddress(toolSetting.getAddress());
        }
    }

    /**
     *  打开编辑窗口
     * @param dubboToolSetting
     * @return
     */
    private DubboToolSetting openDialog(@NotNull DubboToolSetting dubboToolSetting) {
        DubboToolSettingDialog settingDialog = new DubboToolSettingDialog(dubboToolSetting);
        final DialogBuilder dialogBuilder = new DialogBuilder(dubboToolSettingsComponent.getBrowsersTable())
                .title("Dubbo Setting").centerPanel(settingDialog.getPanel());
        if (dialogBuilder.show() == DialogWrapper.OK_EXIT_CODE) {
            DubboToolSetting newSetting = settingDialog.getDubboToolSetting();
            if (isExist(newSetting)) {
                JLabel jLabel = new JLabel("配置已经存在");
                DialogBuilder msgDialog = new DialogBuilder(dubboToolSettingsComponent.getBrowsersTable())
                        .title("Dubbo Setting").centerPanel(jLabel);
                msgDialog.show();
                return null;
            }
            return newSetting;
        }
        return null;
    }

    /**
     * 判断配置是否已经存在，按id判断
     *
     * @param newSetting
     * @return
     */
    private boolean isExist(DubboToolSetting newSetting) {
        List<DubboToolSetting> settingList = dubboToolSettingsComponent.getAllSettings();
        for (DubboToolSetting toolSetting : settingList) {
            if (toolSetting.getId().equals(newSetting.getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void applyEdited(@NotNull DubboToolSetting dubboToolSetting, @NotNull DubboToolSetting t1) {
        dubboToolSetting.setAddress(t1.getAddress());
        dubboToolSetting.setGroup(t1.getGroup());
        dubboToolSetting.setId(t1.getId());
        dubboToolSetting.setName(t1.getName());
        dubboToolSetting.setVersion(t1.getVersion());
        System.out.println("=====================applyEdited=====================");
    }

    @Override
    public @NotNull Class<? extends DubboToolSetting> getItemClass() {
        return DubboToolSetting.class;
    }

    @Override
    public DubboToolSetting clone(@NotNull DubboToolSetting dubboToolSetting, boolean b) {
        DubboToolSetting dubboToolSetting1 = new DubboToolSetting();
         dubboToolSetting1.setId(UUID.randomUUID().toString());
         dubboToolSetting1.setName(dubboToolSetting.getName());
         dubboToolSetting1.setVersion(dubboToolSetting.getVersion());
         dubboToolSetting1.setGroup(dubboToolSetting.getGroup());
         dubboToolSetting1.setAddress(dubboToolSetting.getAddress());
        return dubboToolSetting1;
    }

    @Override
    public boolean isUseDialogToAdd() {
        return true;
    }


}
