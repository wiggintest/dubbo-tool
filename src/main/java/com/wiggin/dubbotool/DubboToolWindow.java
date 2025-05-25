package com.wiggin.dubbotool;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.JBSplitter;
import com.intellij.ui.components.JBTabbedPane;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.wiggin.dubbotool.common.enums.CollectionTypeEnum;
import com.wiggin.dubbotool.ui.DubboInvokePanel;
import com.wiggin.dubbotool.ui.collection.FavoritesListPanel;
import com.wiggin.dubbotool.ui.collection.HistoryListPanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class DubboToolWindow implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        // 创建主面板，分割左右，左边调用历史、收藏，右边是dubbo调用面板
        JPanel mainPanel = new JPanel(new BorderLayout());
        // 左侧面板
        JTabbedPane leftTabbedPane = getLeftPane(project);
        // 右侧dubbo调用面板
        DubboInvokePanel dubboInvokePanel = new DubboInvokePanel(project);

        // 左右面板分割
        JBSplitter splitter = new JBSplitter();
        splitter.setProportion(0.3f);
        splitter.setFirstComponent(leftTabbedPane);
        splitter.setSecondComponent(dubboInvokePanel.getContentPanel());
        mainPanel.add(splitter, BorderLayout.CENTER);

        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(mainPanel, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    private static @NotNull JTabbedPane getLeftPane(@NotNull Project project) {
        JTabbedPane leftTabbedPane = new JBTabbedPane();
        // 左侧调用历史Tab
        HistoryListPanel historyListPanel = new HistoryListPanel(CollectionTypeEnum.HISTORY, project);
        leftTabbedPane.addTab("history", historyListPanel.getComponent());
        // 左侧收藏Tab
        FavoritesListPanel favoritesListPanel = new FavoritesListPanel(CollectionTypeEnum.FAVORITES, project);
        leftTabbedPane.addTab("favorites", favoritesListPanel.getComponent());
        return leftTabbedPane;
    }
}
