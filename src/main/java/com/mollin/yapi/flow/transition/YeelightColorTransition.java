package com.mollin.yapi.flow.transition;

import com.mollin.yapi.utils.YeelightUtils;

/**
 * Represents a color transition (RGB)
 */
public class YeelightColorTransition extends YeelightTransition {
    /**
     * Transition duration
     */
    private int duration;
    /**
     * Transition mode
     */
    private YeelightTransitionMode mode;
    /**
     * Transition value
     */
    private int value;
    /**
     * Transition brightness
     */
    private int brightness;

    /**
     * Color transition constructor
     * @param r Red value [0;255]
     * @param g Green value [0;255]
     * @param b Blue value [0;255]
     * @param duration Transition duration (ms) (&gt;=50)
     * @param brightness Brightness value [1;100]
     */
    public YeelightColorTransition(int r, int g, int b, int duration, int brightness) {
        this.duration = clampDuration(duration);
        this.mode = YeelightTransitionMode.COLOR;
        this.value = YeelightUtils.clampAndComputeRGBValue(r, g, b);
        this.brightness = clampBrightness(brightness);
    }

    /**
     * Color transition constructor
     * @see YeelightColorTransition#YeelightColorTransition(int, int, int, int, int) . Brightness is 100
     * @param r Red value [0;255]
     * @param g Green value [0;255]
     * @param b Blue value [0;255]
     * @param duration Transition duration (ms) (&gt;=50)
     */
    public YeelightColorTransition(int r, int g, int b, int duration) {
        this(r, g, b, duration, 100);
    }

    @Override
    public int getDuration() {
        return this.duration;
    }

    @Override
    public YeelightTransitionMode getMode() {
        return this.mode;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public int getBrightness() {
        return this.brightness;
    }
}
