package com.wiggin.dubbotool.ui;

import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.ui.CheckboxTree;
import com.intellij.ui.CheckedTreeNode;
import com.wiggin.dubbotool.domain.model.ClazzInfoDTO;
import com.wiggin.dubbotool.domain.model.DubboToolContext;
import com.wiggin.dubbotool.domain.model.FieldInfoDO;
import com.wiggin.dubbotool.domain.model.ParameterInfoDO;
import com.wiggin.dubbotool.ui.custom.CustomCheckboxTreeCellRenderer;
import com.wiggin.dubbotool.ui.custom.CustomCheckedTreeNode;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @description:
 * @author: wiggin
 * @date: 2024-08-27 18:49
 **/
public class ClassFieldSelector extends DialogWrapper  {

    private DubboToolContext dubboToolContext;
    private ClazzInfoDTO clazzInfoDTO;
    private JPanel mainPanel;
    private JTree fieldTree;


    public ClassFieldSelector(DubboToolContext dubboToolContext, ClazzInfoDTO clazzInfoDTO) {
        super(dubboToolContext.getProject(),true);
        this.dubboToolContext = dubboToolContext;
        this.clazzInfoDTO = clazzInfoDTO;
        init();
    }


    private void createUIComponents() {
        CheckedTreeNode root = new CheckedTreeNode("Root");
        List<ParameterInfoDO> allParameterInfo = clazzInfoDTO.getParameterInfoDOList();
        allParameterInfo.forEach(e -> root.add(this.getCheckBoxTreeNode(e)));
        // 创建带有复选框的树
        fieldTree = new CheckboxTree(new CustomCheckboxTreeCellRenderer(), root);
    }

    private DefaultMutableTreeNode getCheckBoxTreeNode(ParameterInfoDO parameterInfoDO) {
        CustomCheckedTreeNode checkedTreeNode = new CustomCheckedTreeNode(parameterInfoDO.getParameterName(),parameterInfoDO.getValue(),null);
        List<FieldInfoDO> fieldInfoDOList = parameterInfoDO.getFieldInfoDOList();
        if (Objects.nonNull(fieldInfoDOList) && !fieldInfoDOList.isEmpty()) {
            checkedTreeNode.setClassName(parameterInfoDO.getPsiClass().getQualifiedName());
            for (FieldInfoDO fieldInfoDO : fieldInfoDOList) {
                checkedTreeNode.add(this.getFieldNode(fieldInfoDO));
            }
        }
        return checkedTreeNode;
    }

    private MutableTreeNode getFieldNode(FieldInfoDO fieldInfoDO) {
        CustomCheckedTreeNode checkedTreeNode = new CustomCheckedTreeNode(fieldInfoDO.getFieldName(),fieldInfoDO.getValue(),null);
        List<FieldInfoDO> fieldInfoDOList = fieldInfoDO.getFieldInfoDOList();
        if (Objects.nonNull(fieldInfoDOList) && !fieldInfoDOList.isEmpty()) {
            checkedTreeNode.setClassName(fieldInfoDO.getPsiClass().getQualifiedName());
            for (FieldInfoDO infoDO : fieldInfoDOList) {
                checkedTreeNode.add(this.getFieldNode(infoDO));
            }
        }
        return checkedTreeNode;
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return mainPanel;
    }


    public List<Object> getSelectedFields() {
        List<Object> result = new ArrayList<>();
        TreeModel model = fieldTree.getModel();
        TreeNode root = (TreeNode) model.getRoot();
        for (int i = 0; i < root.getChildCount(); i++) {
            CustomCheckedTreeNode node = (CustomCheckedTreeNode) root.getChildAt(i);
            // 没子节点
            if (node.getChildCount() == 0) {
                result.add(node.getValue());
                continue;
            }
            //  解析子节点
            result.add(this.collectAllNodes(node));
        }
        return result;
    }


    private Object collectAllNodes(TreeNode node) {
        // 遍历子节点
        HashMap<Object, Object> result = new HashMap<>();
        for (int i = 0; i < node.getChildCount(); i++) {
            CustomCheckedTreeNode childNode = (CustomCheckedTreeNode) node.getChildAt(i);
            if (!childNode.isChecked()) {
                continue;
            }
            if (childNode.getChildCount()==0) {
                result.put(childNode.getUserObject(), childNode.getValue());
            } else {
                result.put(childNode.getUserObject(),collectAllNodes(childNode));
            }
        }
        return result;
    }
}
