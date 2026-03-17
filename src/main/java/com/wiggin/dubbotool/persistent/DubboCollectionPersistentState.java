package com.wiggin.dubbotool.persistent;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.wiggin.dubbotool.common.enums.CollectionTypeEnum;
import com.wiggin.dubbotool.domain.model.CollectionPersistentInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @description: 收藏、历史持久化
 * @author: wiggin
 * @date: 2024-10-31 22:05
 **/
@State(
        name = "com.wiggin.dubbotool.persistent.DubboCollectionPersistentState",
        storages = @Storage("DubboToolCollectionPlugin.xml")
)
public class DubboCollectionPersistentState implements PersistentStateComponent<DubboCollectionPersistentState> {
    public Map<String, LinkedList<CollectionPersistentInfo>> collection;

    public DubboCollectionPersistentState() {
        collection = new HashMap<>();
        LinkedList<CollectionPersistentInfo> userCollection = new LinkedList<>();
        LinkedList<CollectionPersistentInfo> history = new LinkedList<>();
        collection.put(CollectionTypeEnum.FAVORITES.getType(), userCollection);
        collection.put(CollectionTypeEnum.HISTORY.getType(), history);
    }

    public static DubboCollectionPersistentState getInstance() {
        return ApplicationManager.getApplication().getService(DubboCollectionPersistentState.class);
    }

    @Override
    public @Nullable DubboCollectionPersistentState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull DubboCollectionPersistentState dubboCollectionPersistentState) {
        collection.put(CollectionTypeEnum.FAVORITES.getType(), dubboCollectionPersistentState.getCollection(CollectionTypeEnum.FAVORITES));
        collection.put(CollectionTypeEnum.HISTORY.getType(), dubboCollectionPersistentState.getCollection(CollectionTypeEnum.HISTORY));
//        XmlSerializerUtil.copyBean(dubboCollectionPersistentState, this);
    }

    private static final int MAX_HISTORY_SIZE = 50;

    /**
     * 添加配置，使用头插入，保证最新操作在最前
     *
     * @param collectionTypeEnum
     * @param collectionPersistentInfo
     */
    public void addCollection(CollectionTypeEnum collectionTypeEnum, CollectionPersistentInfo collectionPersistentInfo) {
        LinkedList<CollectionPersistentInfo> dubboInvokeVOS = collection.get(collectionTypeEnum.getType());
        // 头插入，确保最新的操作放在最前
        dubboInvokeVOS.addFirst(collectionPersistentInfo);
        // 历史记录限制最多50条，超出则删除最老的
        if (collectionTypeEnum == CollectionTypeEnum.HISTORY && dubboInvokeVOS.size() > MAX_HISTORY_SIZE) {
            dubboInvokeVOS.removeLast();
        }
    }

    /**
     * 清除所有
     * @param collectionTypeEnum
     */
    public void clearAll(CollectionTypeEnum collectionTypeEnum) {
        LinkedList<CollectionPersistentInfo> persistentInfos = collection.get(collectionTypeEnum.getType());
        persistentInfos.clear();
    }

    public void remove(CollectionTypeEnum collectionTypeEnum, CollectionPersistentInfo persistentInfo) {
        LinkedList<CollectionPersistentInfo> persistentInfos = collection.get(collectionTypeEnum.getType());
        persistentInfos.remove(persistentInfo);
    }

    /**
     * 通过对应的类型获取集合
     *
     * @param collectionTypeEnum
     * @return
     */
    public LinkedList<CollectionPersistentInfo> getCollection(CollectionTypeEnum collectionTypeEnum) {
        return new LinkedList<>(collection.get(collectionTypeEnum.getType()));
    }
}
