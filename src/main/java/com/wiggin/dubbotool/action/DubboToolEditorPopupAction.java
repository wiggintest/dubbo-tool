package com.wiggin.dubbotool.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.ui.Messages;
import com.wiggin.dubbotool.domain.service.DubboToolService;
import com.wiggin.dubbotool.domain.service.impl.DubboToolServiceImpl;
import com.wiggin.dubbotool.util.JsonUtil;

public class DubboToolEditorPopupAction extends AnAction {

    private final DubboToolService dubboToolService = new DubboToolServiceImpl();

    @Override
    public void actionPerformed(AnActionEvent event) {
        try {
            DumbService dumbService = DumbService.getInstance(event.getProject());
            if (dumbService.isDumb()) {
                Messages.showErrorDialog(event.getProject(), "代码索引中，请索引完毕后再运行", "调用失败");
            } else {
                dubboToolService.prepareForInvoke(event);
            }
        } catch (Exception e) {
            Messages.showErrorDialog(event.getProject(), JsonUtil.toJson(e.getStackTrace()), "调用失败");
        }
    }
}
