package com.wiggin.dubbotool.domain.parser.support;

import java.util.HashSet;
import java.util.Set;

/**
 * Integer、Long、Short、Byte、Float、Double、BigDecimal
 *
 * @description: 数值类型
 * @author: wiggin
 * @date: 2024-09-26 11:32
 **/
public class NumberValueSupport implements ValueSupport {

    @Override
    public Object getValue() {
        return RANDOM.nextInt(128);
    }

    @Override
    public Set<String> getSupportTypes() {
        Set<String> supportTypes = new HashSet<>();
        supportTypes.add(Number.class.getCanonicalName());
        supportTypes.add("int");
        supportTypes.add("long");
        supportTypes.add("short");
        supportTypes.add("byte");
        supportTypes.add("float");
        supportTypes.add("double");
        return supportTypes;
    }

}
