package com.wiggin.dubbotool.domain.parser.support;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class LocalTimeValueSupport implements ValueSupport {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Override
    public Object getValue() {
        return LocalTime.now().format(formatter);
    }

    @Override
    public Set<String> getSupportTypes() {
        return Set.of(LocalTime.class.getCanonicalName());
    }
}
