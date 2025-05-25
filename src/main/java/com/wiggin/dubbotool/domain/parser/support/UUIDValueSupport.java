package com.wiggin.dubbotool.domain.parser.support;

import java.util.Set;
import java.util.UUID;

public class UUIDValueSupport implements ValueSupport {
    @Override
    public Object getValue() {
        return UUID.randomUUID().toString();
    }

    @Override
    public Set<String> getSupportTypes() {
        return Set.of(UUID.class.getCanonicalName());
    }
}
