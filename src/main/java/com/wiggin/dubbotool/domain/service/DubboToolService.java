package com.wiggin.dubbotool.domain.service;

import com.intellij.openapi.actionSystem.AnActionEvent;

public interface DubboToolService {

    /**
     * 为调用准备，唤起UI，获取用户选择等
     *
     * @param anActionEvent
     */
    void prepareForInvoke(AnActionEvent anActionEvent);

}
