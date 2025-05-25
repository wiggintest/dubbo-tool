package com.wiggin.dubbotool.domain.model;

import com.intellij.openapi.project.Project;
import com.intellij.psi.*;

/**
 * @description: 项目上下文
 * @author: wiggin
 * @date: 2024-08-29 16:38
 **/
public class DubboToolContext {

    /**
     * 项目
     */
    private Project project;

    /**
     * psi文件
     */
    private PsiFile psiFile;

    /**
     * psi元素
     */
    private PsiElement psiElement;


    /**
     * psi方法
     */
    private PsiMethod psiMethod;

    /**
     * psi类
     */
    private PsiClass psiClass;


    /**
     * psi参数列表
     */
    private PsiParameterList psiParameterList;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public PsiFile getPsiFile() {
        return psiFile;
    }

    public void setPsiFile(PsiFile psiFile) {
        this.psiFile = psiFile;
    }

    public PsiElement getPsiElement() {
        return psiElement;
    }

    public void setPsiElement(PsiElement psiElement) {
        this.psiElement = psiElement;
    }

    public PsiMethod getPsiMethod() {
        return psiMethod;
    }

    public void setPsiMethod(PsiMethod psiMethod) {
        this.psiMethod = psiMethod;
    }

    public PsiClass getPsiClass() {
        return psiClass;
    }

    public void setPsiClass(PsiClass psiClass) {
        this.psiClass = psiClass;
    }

    public PsiParameterList getPsiParameterList() {
        return psiParameterList;
    }

    public void setPsiParameterList(PsiParameterList psiParameterList) {
        this.psiParameterList = psiParameterList;
    }
}
