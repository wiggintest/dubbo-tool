package com.wiggin.dubbotool.domain.parser.support;

import java.util.Set;

/**
 * @description: 字符串类型
 * @author: wiggin
 * @date: 2024-09-26 17:37
 **/
public class CharSequenceValueSupport implements ValueSupport {
    @Override
    public Object getValue() {
        return "hello_world";
    }

    @Override
    public Set<String> getSupportTypes() {
        return Set.of(CharSequence.class.getCanonicalName());
    }
}
