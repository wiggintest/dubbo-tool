package com.wiggin.dubbotool.domain.parser.support;


import java.util.Set;

public class BooleanValueSupport implements ValueSupport {

    @Override
    public Object getValue() {
        return RANDOM.nextBoolean();
    }

    @Override
    public Set<String> getSupportTypes() {
        return Set.of(Boolean.class.getCanonicalName(), "boolean");
    }
}
