package com.wiggin.dubbotool.domain.service;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.PsiTreeUtil;
import com.wiggin.dubbotool.domain.model.ClazzInfoDTO;
import com.wiggin.dubbotool.domain.model.DubboToolContext;

/**
 * @description: 抽象类
 * @author: wiggin
 * @date: 2024-08-29 17:15
 **/
public abstract class AbstractDubboToolService implements DubboToolService {


    @Override
    public void prepareForInvoke(AnActionEvent anActionEvent) {
        // 初始化上下文
        DubboToolContext dubboToolContext = this.initContext(anActionEvent);
        //  解析得到psi相关信息
        ClazzInfoDTO clazzInfoDTO = this.doParse(dubboToolContext);
        // 唤起窗口，勾选字段
        this.showSelector(dubboToolContext, clazzInfoDTO);
    }

    /**
     * 弹框展示选择字段
     *
     * @param dubboToolContext
     * @param clazzInfoDTO
     */
    protected abstract void showSelector(DubboToolContext dubboToolContext, ClazzInfoDTO clazzInfoDTO);

    /**
     * 解析得到类信息
     *
     * @param dubboToolContext
     * @return
     */
    protected abstract ClazzInfoDTO doParse(DubboToolContext dubboToolContext);

    /**
     * 初始化上下文，保留选中的psifile等信息
     *
     * @param anActionEvent
     * @return
     */
    private DubboToolContext initContext(AnActionEvent anActionEvent) {
        DubboToolContext dubboToolContext = new DubboToolContext();
        dubboToolContext.setProject(anActionEvent.getProject());
        dubboToolContext.setPsiFile(anActionEvent.getData(PlatformDataKeys.PSI_FILE));
        PsiElement element = anActionEvent.getData(PlatformDataKeys.PSI_ELEMENT);
        dubboToolContext.setPsiElement(element);
        PsiMethod psiMethod = (PsiMethod) element;
        dubboToolContext.setPsiMethod(psiMethod);
        dubboToolContext.setPsiClass(PsiTreeUtil.getParentOfType(element, PsiClass.class));
        dubboToolContext.setPsiParameterList(psiMethod.getParameterList());
        return dubboToolContext;
    }
}
