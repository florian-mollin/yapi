package com.mollin.yapi.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Yeelight utility class
 */
public class YeelightUtils {
    /**
     * Gson constant (for JSON reading/writing)
     */
    public static Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    /**
     * Utility class: can not be instanciated
     */
    private YeelightUtils() {
    }

    /**
     * Clamp value in [min, max] interval
     * @param value Value to clamp
     * @param min Min value
     * @param max Max value
     * @return Clamped value
     */
    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Clamp parameters 'r', 'g' and 'b' and then compute rgbValue
     * @param r Red value
     * @param g Green value
     * @param b Blue value
     * @return RGB value
     */
    public static int clampAndComputeRGBValue(int r, int g, int b) {
        r = clamp(r, 0, 255);
        g = clamp(g, 0, 255);
        b = clamp(b, 0, 255);
        return r * 65536 + g * 256 + b;
    }

    /**
     * Return String with all integers from array, separated by delimiter
     * @param delimiter Delimiter
     * @param array Integers array
     * @return String with all integers from array, separated by delimiter
     */
    public static String joinIntArray(String delimiter, int[] array) {
        return Arrays.stream(array).mapToObj(String::valueOf).collect(Collectors.joining(delimiter));
    }

    /**
     * Generate an optional object from JSON data (if predicate is satisfied)
     * @param datas Datas to parse
     * @param validate Predicate for object validation
     * @param clazz Object class to generate
     * @param <T> Type of generated object
     * @return Parsed object if predicate is satisfied (and JSON parser does not produce error)
     */
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
