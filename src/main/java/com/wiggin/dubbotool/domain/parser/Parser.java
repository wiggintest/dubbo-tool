package com.wiggin.dubbotool.domain.parser;


import com.wiggin.dubbotool.domain.model.ClazzInfoDTO;

/**
 * @description: 解析器
 * @author: wiggin
 * @date: 2024-09-21 21:23
 **/
public interface Parser {

    /**
     * 解析psiType
     *
     * @param clazzInfoDTO
     * @return
     */
    void handle(ClazzInfoDTO clazzInfoDTO);

}
