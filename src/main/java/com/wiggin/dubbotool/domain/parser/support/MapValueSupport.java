package com.wiggin.dubbotool.domain.parser.support;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @description: map类型
 * @author: wiggin
 * @date: 2024-09-27 14:40
 **/
public class MapValueSupport implements ValueSupport {
    @Override
    public Object getValue() {
        return new HashMap<>();
    }

    @Override
    public Set<String> getSupportTypes() {
        return Set.of(Map.class.getCanonicalName());
    }
}
