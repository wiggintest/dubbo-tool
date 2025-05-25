package com.wiggin.dubbotool.ui.collection;

import com.intellij.openapi.project.Project;
import com.wiggin.dubbotool.common.enums.CollectionTypeEnum;

/**
 * @description: 收藏
 * @author: wiggin
 * @date: 2025-04-07 13:52
 **/
public class FavoritesListPanel extends AbstractCollectionPanel{
    public FavoritesListPanel(CollectionTypeEnum collectionTypeEnum, Project project) {
        super(collectionTypeEnum, project);
    }
}
