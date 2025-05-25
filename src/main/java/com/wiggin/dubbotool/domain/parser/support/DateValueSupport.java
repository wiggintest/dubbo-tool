package com.wiggin.dubbotool.domain.parser.support;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Set;

public class DateValueSupport implements ValueSupport {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Object getValue() {
        return LocalDateTime.now().format(formatter);
    }

    @Override
    public Set<String> getSupportTypes() {
        return Set.of(Date.class.getCanonicalName());
    }
}
