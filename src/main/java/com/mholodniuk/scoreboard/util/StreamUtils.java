package com.mholodniuk.scoreboard.util;

import java.util.Collection;
import java.util.function.Predicate;

public class StreamUtils {
    public static <T> T findFirstMatchingOrNull(Collection<T> collection, Predicate<T> predicate) {
        return collection.stream().filter(predicate).findFirst().orElse(null);
    }
}
