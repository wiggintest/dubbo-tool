package com.wiggin.dubbotool.persistent;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import com.wiggin.dubbotool.domain.model.DubboToolSetting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @description: 配置持久化
 * @author: wiggin
 * @date: 2024-10-31 22:05
 **/
@State(
        name = "com.wiggin.dubbotool.setting.DubboSettingPersistentStateComponent",
        storages = @Storage("DubboToolSettingsPlugin.xml")
)
public class DubboSettingPersistentState implements PersistentStateComponent<DubboSettingPersistentState> {

    public List<DubboToolSetting> dubboToolSettings = new ArrayList<>();

    public static DubboSettingPersistentState getInstance() {
        return ApplicationManager.getApplication().getService(DubboSettingPersistentState.class);
    }

    @Override
    public @Nullable DubboSettingPersistentState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull DubboSettingPersistentState dubboSettingPersistentState) {
        XmlSerializerUtil.copyBean(dubboSettingPersistentState, this);
    }

    public void setDubboToolSettings(List<DubboToolSetting> dubboToolSettings) {
        this.dubboToolSettings = dubboToolSettings;
    }

    public List<DubboToolSetting> getDubboToolSettings() {
        if (Objects.nonNull(dubboToolSettings) && !dubboToolSettings.isEmpty()) {
            return dubboToolSettings;
        }
        DubboToolSetting dubboToolSetting = this.getDefaultSetting();
        dubboToolSettings.add(dubboToolSetting);
        return dubboToolSettings;
    }

    /**
     * 默认配置
     * @return
     */
    private DubboToolSetting getDefaultSetting() {
        DubboToolSetting toolSetting = new DubboToolSetting();
        toolSetting.setName("Default");
        toolSetting.setVersion("1.0");
        toolSetting.setGroup("");
        toolSetting.setAddress("dubbo://localhost:1111");
        toolSetting.setId(UUID.randomUUID().toString());
        return toolSetting;
    }
}
