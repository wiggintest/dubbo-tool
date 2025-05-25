package com.wiggin.dubbotool.common.util;

import com.wiggin.dubbotool.domain.model.CollectionPersistentInfo;
import com.wiggin.dubbotool.domain.model.DubboInvokeVO;
import com.wiggin.dubbotool.util.JsonUtil;

import java.util.HashMap;

/**
 * @description: 对象转换工具类
 * @author: wiggin
 * @date: 2024-11-13 23:40
 **/
public class ConvertUtils {
    public static CollectionPersistentInfo dubboInvokeVO2CollectionPersistentInfo(DubboInvokeVO dubboInvokeVO) {
        CollectionPersistentInfo collectionPersistentInfo = new CollectionPersistentInfo();
        collectionPersistentInfo.setPersistentName(dubboInvokeVO.getServiceName());
        collectionPersistentInfo.setServiceName(dubboInvokeVO.getServiceName());
        collectionPersistentInfo.setMethodName(dubboInvokeVO.getMethodName());
        collectionPersistentInfo.setGroupName(dubboInvokeVO.getGroupName());
        collectionPersistentInfo.setVersion(dubboInvokeVO.getVersion());
        HashMap<String, Object> panelText = new HashMap<>();
        panelText.put("parameterTypes", dubboInvokeVO.getParameterTypes());
        panelText.put("param", dubboInvokeVO.getParam());
        collectionPersistentInfo.setParam(JsonUtil.toJson(panelText));
        collectionPersistentInfo.setAddress(dubboInvokeVO.getAddress());
        return collectionPersistentInfo;
    }

    public static DubboInvokeVO collectionPersistentInfo2DubboInvokeVO(CollectionPersistentInfo collectionPersistentInfo) {
        DubboInvokeVO dubboInvokeVO = JsonUtil.fromJson(collectionPersistentInfo.getParam(), DubboInvokeVO.class);
        dubboInvokeVO.setServiceName(collectionPersistentInfo.getServiceName());
        dubboInvokeVO.setMethodName(collectionPersistentInfo.getMethodName());
        dubboInvokeVO.setGroupName(collectionPersistentInfo.getGroupName());
        dubboInvokeVO.setVersion(collectionPersistentInfo.getVersion());
        dubboInvokeVO.setAddress(collectionPersistentInfo.getAddress());
        return dubboInvokeVO;
    }
}
