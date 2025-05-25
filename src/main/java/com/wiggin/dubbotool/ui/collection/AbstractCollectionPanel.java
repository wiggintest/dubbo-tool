package com.wiggin.dubbotool.ui.collection;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.ui.PopupHandler;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.wiggin.dubbotool.common.enums.CollectionTypeEnum;
import com.wiggin.dubbotool.common.util.ConvertUtils;
import com.wiggin.dubbotool.domain.model.CollectionPersistentInfo;
import com.wiggin.dubbotool.domain.model.DubboInvokeVO;
import com.wiggin.dubbotool.persistent.DubboCollectionPersistentState;
import com.wiggin.dubbotool.ui.listener.CollectionPanelListener;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import static com.wiggin.dubbotool.common.constant.TopicConstant.COLLECTION_PANEL_LISTENER_TOPIC;
import static com.wiggin.dubbotool.common.constant.TopicConstant.DUBBO_INVOKE_REFRESH_TOPIC;

/**
 * @description: 收藏、历史
 * @author: wiggin
 * @date: 2025-04-07 10:45
 **/
public abstract class AbstractCollectionPanel {
    private final CollectionTypeEnum collectionTypeEnum;
    private final JList<CollectionPersistentInfo> data;
    private JBScrollPane jScrollBar;

    public AbstractCollectionPanel(CollectionTypeEnum collectionTypeEnum, Project project) {
        this.collectionTypeEnum = collectionTypeEnum;
        DefaultListModel<CollectionPersistentInfo> model = this.loadData();
        data = new JBList<>(model);
        jScrollBar = new JBScrollPane(data);
        init(project, model);
    }

    /**
     * 初始化操作
     * @param project
     * @param model
     */
    private void init(Project project, DefaultListModel<CollectionPersistentInfo> model) {
        data.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.addMouseListener(data,project);
        // 添加删除菜单
        this.addDeleteMenu(model,data);
//        this.addListSelectionListener(project);
        project.getMessageBus().connect().subscribe(COLLECTION_PANEL_LISTENER_TOPIC, (CollectionPanelListener) (collectionPersistentInfo, collectionTypeEnum1) -> {
            if (collectionTypeEnum1 == this.collectionTypeEnum) {
                model.insertElementAt(collectionPersistentInfo,0);
            }
        });
    }

    /**
     * 左键点击刷新右侧面板，右键点击弹出删除按钮
     * @param data
     */
    private void addMouseListener(JList<CollectionPersistentInfo> data,Project project) {
        data.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // 左键点击刷新右侧面板
                if (SwingUtilities.isLeftMouseButton(e)) {
                    int index = data.locationToIndex(e.getPoint());
                    if (index != -1 && data.getCellBounds(index, index).contains(e.getPoint())) {
                        CollectionPersistentInfo selectedValue = data.getModel().getElementAt(index);
                        if (selectedValue == null) {
                            return;
                        }
                        DubboInvokeVO dubboInvokeVO = ConvertUtils.collectionPersistentInfo2DubboInvokeVO(selectedValue);
                        // 通知右侧面板更新
                        project.getMessageBus().syncPublisher(DUBBO_INVOKE_REFRESH_TOPIC).onSelectionChanged(dubboInvokeVO);
                    }
                }
            }
        });
    }

    /**
     * 添加删除菜单
     * @param model
     * @param data
     */
    private void addDeleteMenu(DefaultListModel<CollectionPersistentInfo> model, JList<CollectionPersistentInfo> data) {
        // 添加右键菜单
        DefaultActionGroup actionGroup = new DefaultActionGroup();
        actionGroup.add(new AnAction("Delete") {
            @Override
            public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
                int selectedIndex = data.getSelectedIndex();
                if (selectedIndex >= 0) {
                    CollectionPersistentInfo removed = model.remove(selectedIndex);
                    // 删除持久化
                    DubboCollectionPersistentState.getInstance().remove(collectionTypeEnum, removed);
                }
            }
        });
        // 注册弹出菜单
        PopupHandler popupHandler = new PopupHandler() {

            @Override
            public void invokePopup(Component comp, int x, int y) {
                int index = data.locationToIndex(comp.getMousePosition());
                if (index >= 0 && data.getCellBounds(index, index).contains(comp.getMousePosition())) {
                    data.setSelectedIndex(index);
                    ActionManager.getInstance().createActionPopupMenu("MyListPopup", actionGroup)
                            .getComponent()
                            .show(comp, x, y);
                }
            }
        };
        data.addMouseListener(popupHandler);
    }


    /**
     * 选中监听，刷新右侧调用面板
     */
    private void addListSelectionListener(Project project) {
        data.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                CollectionPersistentInfo selectedValue = data.getSelectedValue();
                if (selectedValue == null) {
                    return;
                }
                DubboInvokeVO dubboInvokeVO = ConvertUtils.collectionPersistentInfo2DubboInvokeVO(selectedValue);
                // 通知右侧面板更新
                project.getMessageBus().syncPublisher(DUBBO_INVOKE_REFRESH_TOPIC).onSelectionChanged(dubboInvokeVO);
                data.clearSelection();
            }
        });
    }


    /**
     * 加载数据
     *
     * @return
     */
    protected DefaultListModel<CollectionPersistentInfo> loadData() {
        LinkedList<CollectionPersistentInfo> collection = DubboCollectionPersistentState.getInstance().getCollection(collectionTypeEnum);
        DefaultListModel<CollectionPersistentInfo> collectionPersistentInfoListModel = new DefaultListModel<>();
        for (CollectionPersistentInfo collectionPersistentInfo : collection) {
            collectionPersistentInfoListModel.addElement(collectionPersistentInfo);
        }
        return collectionPersistentInfoListModel;
    }

    public JComponent getComponent() {
        return jScrollBar;
    }
}
