package com.wiggin.dubbotool.domain.parser.support;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CollectionValueSupport implements ValueSupport {

    @Override
    public Object getValue() {
        return List.of();
    }

    @Override
    public Set<String> getSupportTypes() {
        return Set.of(Collection.class.getCanonicalName());
    }

}
