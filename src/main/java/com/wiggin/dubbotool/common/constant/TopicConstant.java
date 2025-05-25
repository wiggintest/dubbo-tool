package com.wiggin.dubbotool.common.constant;

import com.intellij.util.messages.Topic;
import com.wiggin.dubbotool.ui.listener.CollectionPanelListener;
import com.wiggin.dubbotool.ui.listener.DubboInvokePanelListener;
import com.wiggin.dubbotool.ui.listener.SettingChangeListener;

/**
 * @description: topic常量
 * @author: wiggin
 * @date: 2025-04-07 13:39
 **/
public class TopicConstant {

    /**
     * 刷新dubbo调用面板topic
     */
    public static final Topic<DubboInvokePanelListener> DUBBO_INVOKE_REFRESH_TOPIC =
            new Topic<>("DubboInvoke refresh UI", DubboInvokePanelListener.class);

    /**
     * 收藏、历史变更，同时左边刷新topic
     */
    public static final Topic<CollectionPanelListener> COLLECTION_PANEL_LISTENER_TOPIC =
            new Topic<>("collection refresh", CollectionPanelListener.class);

    public static final Topic<SettingChangeListener> SETTING_CHANGE_TOPIC =
            new Topic<>("setting change", SettingChangeListener.class);
}
