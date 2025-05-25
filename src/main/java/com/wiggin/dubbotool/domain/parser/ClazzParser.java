package com.wiggin.dubbotool.domain.parser;


import com.wiggin.dubbotool.domain.model.ClazzInfoDTO;
import com.wiggin.dubbotool.domain.model.DubboToolContext;

/**
 * @description: 类解析
 * @author: wiggin
 * @date: 2024-09-21 22:39
 **/
public class ClazzParser extends AbstractParser {

    public ClazzParser(DubboToolContext dubboToolContext) {
        super(dubboToolContext);
    }

    @Override
    public void doParser(ClazzInfoDTO clazzInfoDTO) {
        clazzInfoDTO.setClazzName(dubboToolContext.getPsiClass().getQualifiedName());
    }
}
