package com.wiggin.dubbotool.domain.parser.support;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

public class LocalDateValueSupport implements ValueSupport {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Override
    public Object getValue() {
        return LocalDate.now().format(formatter);
    }

    @Override
    public Set<String> getSupportTypes() {
        return Set.of(LocalDate.class.getCanonicalName());
    }
}
