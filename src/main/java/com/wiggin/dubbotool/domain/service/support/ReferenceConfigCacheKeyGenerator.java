package com.wiggin.dubbotool.domain.service.support;

import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.google.common.collect.Lists;
import com.wiggin.dubbotool.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @description: 缓存key
 * @author: wiggin
 * @date: 2024-10-18 00:44
 **/
public class ReferenceConfigCacheKeyGenerator implements ReferenceConfigCache.KeyGenerator {
    @Override
    public String generateKey(ReferenceConfig<?> referenceConfig) {
        String interfaceName = referenceConfig.getInterface();
        if (StringUtils.isBlank(interfaceName)) {
            Class<?> clazz = referenceConfig.getInterfaceClass();
            interfaceName = clazz.getName();
        }
        if (StringUtils.isBlank(interfaceName)) {
            throw new IllegalArgumentException("No interface info in ReferenceConfig" + referenceConfig);
        }

        String group = StringUtils.defaultString(referenceConfig.getGroup());
        String version = StringUtils.defaultString(referenceConfig.getVersion());
        String url = StringUtils.defaultString(referenceConfig.getUrl());
        String registries = StringUtils.defaultString(
                JsonUtil.toJson(referenceConfig.getRegistries()));
        List<String> uniqueParams = Lists.newArrayList(interfaceName, group, version, url, registries);
        return String.join("_", uniqueParams);
    }
}
