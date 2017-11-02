package com.mollin.yapi.utils;

public class YeelightUtils {
    private YeelightUtils() {
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
