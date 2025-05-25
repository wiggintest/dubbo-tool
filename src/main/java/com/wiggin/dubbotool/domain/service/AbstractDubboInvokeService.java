package com.wiggin.dubbotool.domain.service;


import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.utils.ReferenceConfigCache;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.wiggin.dubbotool.domain.model.DubboInvokeVO;
import com.wiggin.dubbotool.domain.service.support.ReferenceConfigCacheKeyGenerator;
import com.wiggin.dubbotool.util.JsonUtil;

import static com.wiggin.dubbotool.common.constant.DubboToolConstant.PLUGIN_NAME;

/**
 * @description: dubbo调用
 * @author: wiggin
 * @date: 2024-10-17 22:17
 **/
public abstract class AbstractDubboInvokeService implements DubboInvokeService {

    protected static final String ZOOKEEPER_PREFIX = "zookeeper";
    protected static final String DUBBO_PREFIX = "dubbo";

    public static final ReferenceConfigCache.KeyGenerator KEY_GENERATOR = new ReferenceConfigCacheKeyGenerator();

    @Override
    public String invoke(DubboInvokeVO dubboInvokeVO) {
        ReferenceConfig<GenericService> referenceConfig = this.getReferenceConfig(dubboInvokeVO);
        ReferenceConfigCache cache = ReferenceConfigCache.getCache(PLUGIN_NAME, KEY_GENERATOR);
        GenericService genericService = cache.get(referenceConfig);
        Object result = genericService.$invoke(dubboInvokeVO.getMethodName(), dubboInvokeVO.getParameterTypes(), dubboInvokeVO.getParam());
        return JsonUtil.toJson(result);
    }

    private ReferenceConfig<GenericService> getReferenceConfig(DubboInvokeVO invokeVO) {
        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setInterface(invokeVO.getServiceName());
        reference.setCheck(false);
        reference.setGeneric("true");
        reference.setRetries(0);
        reference.setTimeout(60 * 1000);
        if (invokeVO.getAddress().startsWith(ZOOKEEPER_PREFIX)) {
            reference.setRegistry(new RegistryConfig(invokeVO.getAddress()));
        } else {
            reference.setUrl(invokeVO.getAddress());
        }
        reference.setVersion(invokeVO.getVersion());
        if (!"*".equals(invokeVO.getGroupName())) {
            reference.setGroup(invokeVO.getGroupName());
        }
        reference.setApplication(new ApplicationConfig(PLUGIN_NAME));
        reference.setProtocol("dubbo");
        return reference;
    }
}
