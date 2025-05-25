package com.wiggin.dubbotool.domain.parser.support;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ValueSupportFactory {
    private static final Map<String, ValueSupport> VALUE_SUPPORT_MAP = new HashMap<>();

    static {
        Reflections reflections = new Reflections(ValueSupport.class.getPackageName());
        Set<Class<? extends ValueSupport>> subTypesOf = reflections.getSubTypesOf(ValueSupport.class);
        for (Class<? extends ValueSupport> clazz : subTypesOf) {
            try {
                ValueSupport valueSupport = clazz.getDeclaredConstructor().newInstance();
                Set<String> supportTypes = valueSupport.getSupportTypes();
                for (String supportType : supportTypes) {
                    VALUE_SUPPORT_MAP.put(supportType, valueSupport);
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 获取支持的值
     *
     * @param type
     * @return
     */
    public static ValueSupport getValueSupport(String type) {
        return VALUE_SUPPORT_MAP.get(type);
    }
}
