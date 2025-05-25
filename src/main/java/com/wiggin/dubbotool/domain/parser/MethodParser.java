package com.wiggin.dubbotool.domain.parser;

import com.wiggin.dubbotool.domain.model.ClazzInfoDTO;
import com.wiggin.dubbotool.domain.model.DubboToolContext;

/**
 * @description: 方法解析
 * @author: wiggin
 * @date: 2024-09-22 10:50
 **/
public class MethodParser extends AbstractParser {
    public MethodParser(DubboToolContext dubboToolContext) {
        super(dubboToolContext);
    }

    @Override
    public void doParser(ClazzInfoDTO clazzInfoDTO) {
        clazzInfoDTO.setMethodName(dubboToolContext.getPsiMethod().getName());
    }
}
