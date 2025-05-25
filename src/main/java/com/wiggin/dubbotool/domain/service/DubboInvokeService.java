package com.wiggin.dubbotool.domain.service;


import com.wiggin.dubbotool.domain.model.DubboInvokeVO;

/**
 * @description: dubbo调用
 * @author: wiggin
 * @date: 2024-10-17 22:17
 **/
public interface DubboInvokeService {

    String invoke(DubboInvokeVO dubboInvokeVO);

}
