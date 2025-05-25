package com.wiggin.dubbotool.ui.listener;


import com.wiggin.dubbotool.common.enums.CollectionTypeEnum;
import com.wiggin.dubbotool.domain.model.CollectionPersistentInfo;

/**
 * @description:
 * @author: wiggin
 * @date: 2025-04-07 16:07
 **/
public interface CollectionPanelListener {

    void changed(CollectionPersistentInfo collectionPersistentInfo, CollectionTypeEnum collectionTypeEnum);

}
