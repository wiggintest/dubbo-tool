package com.wiggin.dubbotool.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.JBSplitter;
import com.wiggin.dubbotool.common.constant.TopicConstant;
import com.wiggin.dubbotool.common.enums.CollectionTypeEnum;
import com.wiggin.dubbotool.common.util.ConvertUtils;
import com.wiggin.dubbotool.common.util.StackTraceUtil;
import com.wiggin.dubbotool.domain.model.CollectionPersistentInfo;
import com.wiggin.dubbotool.domain.model.DubboInvokeVO;
import com.wiggin.dubbotool.domain.model.DubboToolSetting;
import com.wiggin.dubbotool.domain.service.DubboInvokeService;
import com.wiggin.dubbotool.domain.service.impl.DubboInvokeServiceImpl;
import com.wiggin.dubbotool.persistent.DubboCollectionPersistentState;
import com.wiggin.dubbotool.persistent.DubboSettingPersistentState;
import com.wiggin.dubbotool.ui.custom.CustomAddressComboBoxRenderer;
import com.wiggin.dubbotool.ui.custom.CustomJsonEditorPanel;
import com.wiggin.dubbotool.ui.listener.DubboInvokePanelListener;
import com.wiggin.dubbotool.ui.listener.SettingChangeListener;
import com.wiggin.dubbotool.util.JsonUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.wiggin.dubbotool.common.constant.TopicConstant.COLLECTION_PANEL_LISTENER_TOPIC;

/**
 * @description:
 * @author: wiggin
 * @date: 2024-09-29 16:23
 **/
public class DubboInvokePanel {
    private JPanel mainPanel;
    private JTextField serviceNameField;
    private JTextField versionTextField;
    private JTextField groupTextField;
    private JTextField methodNameTextField;
    private JPanel editorPane;
    private JButton invokeButton;
    private JComboBox<DubboToolSetting> address;
    private JButton saveAsButton;
    private CustomJsonEditorPanel paramsField;
    private CustomJsonEditorPanel responseField;

    private final DubboInvokeService dubboInvokeService = new DubboInvokeServiceImpl();
    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2, 8, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>(), new ThreadPoolExecutor.AbortPolicy());

    public DubboInvokePanel(Project project) {
        // 监听调用事件
        this.addInvokeListener(project);
        // 监听保存事件
        this.addSaveListener(project);
        // 初始化面板
        this.initEditorPanel(project);
        // 监听消息，刷新面板
        project.getMessageBus().connect().subscribe(TopicConstant.DUBBO_INVOKE_REFRESH_TOPIC, (DubboInvokePanelListener) this::refreshUI);
    }

    private void addSaveListener(Project project) {
        this.saveAsButton.addActionListener(e -> {
            String input = Messages.showInputDialog("", "请输入保存名称", Messages.getQuestionIcon(), serviceNameField.getText(), null);
            if (Objects.nonNull(input)) {
                // 持久化到收藏
                CollectionPersistentInfo collectionPersistentInfo = ConvertUtils.dubboInvokeVO2CollectionPersistentInfo(this.getDubboInvokeVO());
                collectionPersistentInfo.setPersistentName(input);
                collectionPersistentInfo.setId(UUID.randomUUID().toString());
                DubboCollectionPersistentState.getInstance().addCollection(CollectionTypeEnum.FAVORITES, collectionPersistentInfo);
                // 发布topic
                project.getMessageBus().syncPublisher(COLLECTION_PANEL_LISTENER_TOPIC).changed(collectionPersistentInfo, CollectionTypeEnum.FAVORITES);
            }
        });
    }

    /**
     * 刷新ui
     *
     * @param dubboInvokeVO
     */
    private void refreshUI(DubboInvokeVO dubboInvokeVO) {
        serviceNameField.setText(dubboInvokeVO.getServiceName());
        methodNameTextField.setText(dubboInvokeVO.getMethodName());
        HashMap<String, Object> panelText = new HashMap<>();
        panelText.put("parameterTypes", dubboInvokeVO.getParameterTypes());
        panelText.put("param", dubboInvokeVO.getParam());
        paramsField.writeDocumentText(JsonUtil.toJson(panelText));
        if (Objects.nonNull(dubboInvokeVO.getAddress())) {
            // 在列表中查找匹配的地址并选中
            boolean found = false;
            for (int i = 0; i < address.getItemCount(); i++) {
                DubboToolSetting item = address.getItemAt(i);
                if (item != null && dubboInvokeVO.getAddress().equals(item.getAddress())) {
                    address.setSelectedIndex(i);
                    found = true;
                    break;
                }
            }
            if (!found) {
                // 地址不在列表中，使用编辑器显示
                address.getEditor().setItem(dubboInvokeVO.getAddress());
            }
        } else {
            address.setSelectedIndex(0);
        }
        responseField.writeDocumentText("");
    }

    /**
     * 初始化编辑器面板
     */
    private void initEditorPanel(Project project) {
        JBSplitter mContentSplitter = new JBSplitter();
        mContentSplitter.setProportion(0.5f);
        paramsField = new CustomJsonEditorPanel(project);
        paramsField.setBorder(BorderFactory.createTitledBorder("Request"));
        responseField = new CustomJsonEditorPanel(project);
        responseField.setBorder(BorderFactory.createTitledBorder("Response"));
        mContentSplitter.setFirstComponent(paramsField);
        mContentSplitter.setSecondComponent(responseField);
        editorPane.add(mContentSplitter, BorderLayout.CENTER);
        initAddress(project);
    }

    private void initAddress(Project project) {
        DubboSettingPersistentState instance = DubboSettingPersistentState.getInstance();
        List<DubboToolSetting> dubboToolSettings = instance.getDubboToolSettings();
        for (DubboToolSetting dubboToolSetting : dubboToolSettings) {
            address.addItem(dubboToolSetting);
        }

        address.setRenderer(new CustomAddressComboBoxRenderer());
        // 添加监听器，选中后修改group跟version
        address.addItemListener(itemEvent -> {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                try {
                    DubboToolSetting selectedItem = (DubboToolSetting) address.getSelectedItem();
                    versionTextField.setText(selectedItem.getVersion());
                    groupTextField.setText(selectedItem.getGroup());
                } catch (ClassCastException e) {
                    // 修改了combox内容，转换异常忽略
                }
            }
        });
        // 监听变更
        project.getMessageBus().connect().subscribe(TopicConstant.SETTING_CHANGE_TOPIC, (SettingChangeListener) () -> {
            address.removeAllItems();
            List<DubboToolSetting> settings = instance.getDubboToolSettings();
            for (DubboToolSetting dubboToolSetting : settings) {
                address.addItem(dubboToolSetting);
            }
        });
    }


    private void addInvokeListener(Project project) {
        invokeButton.addActionListener(e -> {
            DubboInvokeVO invokeVO = this.getDubboInvokeVO();
            // 异步调用
            CompletableFuture.runAsync(() -> {
                try {
                    String result = dubboInvokeService.invoke(invokeVO);
                    responseField.writeDocumentText(result);
                } catch (Exception exception) {
                    // 保存调用历史
                    CollectionPersistentInfo collectionPersistentInfo = ConvertUtils.dubboInvokeVO2CollectionPersistentInfo(invokeVO);
                    collectionPersistentInfo.setId(UUID.randomUUID().toString());
                    DubboCollectionPersistentState.getInstance().addCollection(CollectionTypeEnum.HISTORY, collectionPersistentInfo);
                    responseField.writeDocumentText(StackTraceUtil.getStackTraceAsString(exception));
                    // 通知刷新UI
                    project.getMessageBus().syncPublisher(COLLECTION_PANEL_LISTENER_TOPIC).changed(collectionPersistentInfo, CollectionTypeEnum.HISTORY);
                }
            }, threadPoolExecutor);
        });
    }

    private @NotNull DubboInvokeVO getDubboInvokeVO() {
        DubboInvokeVO invokeVO = JsonUtil.fromJson(paramsField.getDocumentText(), DubboInvokeVO.class);
        invokeVO.setServiceName(serviceNameField.getText());
        invokeVO.setMethodName(methodNameTextField.getText());
        invokeVO.setGroupName(groupTextField.getText());
        invokeVO.setVersion(versionTextField.getText());
        invokeVO.setAddress(address.getSelectedItem().toString());
        return invokeVO;
    }

    public @Nullable JComponent getContentPanel() {
        return mainPanel;
    }
}
