package com.mollin.yapi.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Optional;
import java.util.function.Predicate;

public class YeelightUtils {
    public static Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    private YeelightUtils() {
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public static <T> Optional<T> generateFromJsonDatas(String datas, Predicate<T> validate, Class<T> clazz) {
        try {
            T t = GSON.fromJson(datas, clazz);
            if (validate.test(t)) {
                return Optional.of(t);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
