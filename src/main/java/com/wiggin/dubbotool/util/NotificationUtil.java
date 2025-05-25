package com.wiggin.dubbotool.util;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.AnActionEvent;

import static com.wiggin.dubbotool.common.constant.DubboToolConstant.NOTIFICATION_GROUP_ID;

/**
 * @description: 告警工具
 * @author: wiggin
 * @date: 2024-08-27 13:45
 **/
public class NotificationUtil {

    public static void notify(AnActionEvent event, String content) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup(NOTIFICATION_GROUP_ID)
                .createNotification(content, NotificationType.ERROR)
                .notify(event.getProject());
    }

}
