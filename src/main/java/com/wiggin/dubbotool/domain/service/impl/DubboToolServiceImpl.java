package com.wiggin.dubbotool.domain.service.impl;

import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.wiggin.dubbotool.common.constant.TopicConstant;
import com.wiggin.dubbotool.domain.model.ClazzInfoDTO;
import com.wiggin.dubbotool.domain.model.DubboInvokeVO;
import com.wiggin.dubbotool.domain.model.DubboToolContext;
import com.wiggin.dubbotool.domain.model.ParameterInfoDO;
import com.wiggin.dubbotool.domain.parser.*;
import com.wiggin.dubbotool.domain.service.AbstractDubboToolService;
import com.wiggin.dubbotool.ui.ClassFieldSelector;

import java.util.List;

import static com.wiggin.dubbotool.common.constant.DubboToolConstant.DUBBO_TOOL_WINDOW_ID;

/**
 * @description: 实现类
 * @author: wiggin
 * @date: 2024-09-13 11:18
 **/
public class DubboToolServiceImpl extends AbstractDubboToolService {


    @Override
    protected void showSelector(DubboToolContext dubboToolContext, ClazzInfoDTO clazzInfoDTO) {
        ClassFieldSelector classFieldSelector = new ClassFieldSelector(dubboToolContext, clazzInfoDTO);
        if (classFieldSelector.showAndGet()) {
            // 选择了确定，获取dubbo调用的参数
            List<Object> selectedFields = classFieldSelector.getSelectedFields();
            DubboInvokeVO dubboInvokeVO = getInvokeVO(clazzInfoDTO, selectedFields);
            ToolWindow toolWindow = ToolWindowManager.getInstance(dubboToolContext.getProject()).getToolWindow(DUBBO_TOOL_WINDOW_ID);
            toolWindow.show();
            dubboToolContext.getProject().getMessageBus().syncPublisher(TopicConstant.DUBBO_INVOKE_REFRESH_TOPIC).onSelectionChanged(dubboInvokeVO);
        }
    }

    private DubboInvokeVO getInvokeVO(ClazzInfoDTO clazzInfoDTO, List<Object> selectedFields) {
        DubboInvokeVO dubboInvokeVO = new DubboInvokeVO();
        dubboInvokeVO.setServiceName(clazzInfoDTO.getClazzName());
        dubboInvokeVO.setMethodName(clazzInfoDTO.getMethodName());
        List<ParameterInfoDO> parameterInfoDOList = clazzInfoDTO.getParameterInfoDOList();
        String[] parameterTypes = parameterInfoDOList.stream().map(ParameterInfoDO::getParameterType).toArray(String[]::new);
        dubboInvokeVO.setParameterTypes(parameterTypes);
        dubboInvokeVO.setParam(selectedFields.toArray());
        return dubboInvokeVO;
    }

    @Override
    protected ClazzInfoDTO doParse(DubboToolContext dubboToolContext) {
        Parser parser = this.getParser(dubboToolContext);
        ClazzInfoDTO clazzInfoDTO = new ClazzInfoDTO();
        parser.handle(clazzInfoDTO);
        return clazzInfoDTO;
    }

    private Parser getParser(DubboToolContext dubboToolContext) {
        ClazzParser clazzParser = new ClazzParser(dubboToolContext);
        MethodParser methodParser = new MethodParser(dubboToolContext);
        ParameterParser parameterParser = new ParameterParser(dubboToolContext);
        FieldParser fieldParser = new FieldParser(dubboToolContext);
        parameterParser.setNext(fieldParser);
        methodParser.setNext(parameterParser);
        clazzParser.setNext(methodParser);
        return clazzParser;
    }
}
