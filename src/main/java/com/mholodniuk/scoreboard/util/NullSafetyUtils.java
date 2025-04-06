package com.mholodniuk.scoreboard.util;

public class NullSafetyUtils {
    public static void requireNonNull(String message, Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                throw new IllegalArgumentException(message);
            }
        }
    }

    public static void requireNonNull(Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                throw new IllegalArgumentException("Objects cannot be null");
            }
        }
    }
}
