package com.wiggin.dubbotool.ui.custom;

import com.intellij.json.JsonFileType;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.impl.text.PsiAwareTextEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.ui.components.panels.NonOpaquePanel;

/**
 * @description: json
 * @author: wiggin
 * @date: 2024-10-19 13:43
 **/
public class CustomJsonEditorPanel extends NonOpaquePanel {
    /** Psi file */
    private final PsiFile psiFile;
    /** Project */
    private final Project project;

    /**
     * Json editor
     *
     * @param project project
     * @since 1.0.0
     */
    public CustomJsonEditorPanel(Project project) {
        this.project = project;
        this.psiFile = this.createPsiFile();
        VirtualFile virtualFile = this.psiFile.getVirtualFile();
        FileEditor fileEditor = this.createFileEditor(virtualFile);
        this.add(fileEditor.getComponent(), "Center");
    }

    /**
     * Create file editor
     *
     * @param virtualFile virtual file
     * @return the file editor
     * @since 1.0.0
     */
    private FileEditor createFileEditor(VirtualFile virtualFile) {
        return PsiAwareTextEditorProvider.getInstance().createEditor(this.project, virtualFile);
    }

    /**
     * Get document text
     *
     * @return the string
     * @since 1.0.0
     */
    public String getDocumentText() {
        Document document = this.getDocument();
        return document.getText();
    }

    /**
     * Get document
     *
     * @return the document
     * @since 1.0.0
     */
    public Document getDocument() {
        PsiDocumentManager instance = PsiDocumentManager.getInstance(this.project);
        return instance.getDocument(this.psiFile);
    }

    public void writeDocumentText(String text) {
        Document document = this.getDocument();
        WriteCommandAction.runWriteCommandAction(project, () -> document.setText(text));
    }

    /**
     * Create psi file
     *
     * @return the psi file
     * @since 1.0.0
     */
    private PsiFile createPsiFile() {
        JsonFileType fileType = JsonFileType.INSTANCE;
        PsiFile psiFile = PsiFileFactory.getInstance(this.project)
                .createFileFromText("tmp." + fileType.getDefaultExtension()
                        , fileType.getLanguage()
                        , "{}"
                        , true
                        , false);
        psiFile.putUserData(Key.create("JSON_HELPER"), "TEST");
        return psiFile;
    }

}
