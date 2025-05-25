package com.wiggin.dubbotool.ui.listener;

import com.wiggin.dubbotool.domain.model.DubboInvokeVO;

/**
 * @description: dubbo调用面板监听器
 * @author: wiggin
 * @date: 2025-04-07 11:11
 **/
public interface DubboInvokePanelListener {
    void onSelectionChanged(DubboInvokeVO selectedItem);
}
