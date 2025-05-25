package com.wiggin.dubbotool.domain.parser.support;

import java.util.Set;

/**
 * @description: char类型
 * @author: wiggin
 * @date: 2024-09-26 17:38
 **/
public class CharValueSupport implements ValueSupport {
    @Override
    public Object getValue() {
        return 'a';
    }

    @Override
    public Set<String> getSupportTypes() {
        return Set.of("char");
    }
}
