package com.wiggin.dubbotool.domain.parser.support;

import java.util.Random;
import java.util.Set;

public interface ValueSupport {

    Random RANDOM = new Random();

    Object getValue();

    Set<String> getSupportTypes();
}
