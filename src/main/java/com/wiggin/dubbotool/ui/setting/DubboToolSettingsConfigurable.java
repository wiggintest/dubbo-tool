package com.wiggin.dubbotool.ui.setting;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsContexts;
import com.wiggin.dubbotool.domain.model.DubboToolSetting;
import com.wiggin.dubbotool.persistent.DubboSettingPersistentState;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

import static com.wiggin.dubbotool.common.constant.DubboToolConstant.PLUGIN_NAME;
import static com.wiggin.dubbotool.common.constant.TopicConstant.SETTING_CHANGE_TOPIC;

/**
 * @description:
 * @author: wiggin
 * @date: 2024-11-02 09:35
 **/
public class DubboToolSettingsConfigurable implements Configurable {

    private DubboToolSettingsComponent dubboToolSettingsComponent;

    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return PLUGIN_NAME;
    }

    @Override
    public @Nullable JComponent createComponent() {
        dubboToolSettingsComponent = new DubboToolSettingsComponent();
        return dubboToolSettingsComponent.getComponent();
    }

    @Override
    public boolean isModified() {
        return dubboToolSettingsComponent.isModified();
    }

    @Override
    public void apply() throws ConfigurationException {
        List<DubboToolSetting> allSettings = dubboToolSettingsComponent.getAllSettings();
        DubboSettingPersistentState.getInstance().setDubboToolSettings(allSettings);
        // 获取当前数据上下文
        DataManager dataManager = DataManager.getInstance();
        DataContext dataContext = dataManager.getDataContext(dubboToolSettingsComponent.getComponent());
        // 从数据上下文中获取 Project
        Project project = CommonDataKeys.PROJECT.getData(dataContext);
        project.getMessageBus().syncPublisher(SETTING_CHANGE_TOPIC).changed();
    }

    @Override
    public void reset() {
        DubboSettingPersistentState instance = DubboSettingPersistentState.getInstance();
        List<DubboToolSetting> dubboToolSettings = instance.getDubboToolSettings();
        dubboToolSettingsComponent.reset(dubboToolSettings);
    }


}
