package com.mollin.yapi.flow.transition;

import com.mollin.yapi.utils.YeelightUtils;

/**
 * Represents a color temperature transition
 */
public class YeelightColorTemperatureTransition extends YeelightTransition {
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
     * Color temperature transition constructor
     * @param colorTemp Color temperature value [1700;6500]
     * @param duration Transition duration (ms) (&gt;=50)
     * @param brightness Brightness value [1;100]
     */
    public YeelightColorTemperatureTransition(int colorTemp, int duration, int brightness) {
        this.duration = clampDuration(duration);
        this.mode = YeelightTransitionMode.COLOR_TEMPERATURE;
        this.value = YeelightUtils.clamp(colorTemp, 1700, 6500);
        this.brightness = clampBrightness(brightness);
    }

    /**
     * Color temperature transition constructor
     * @see YeelightColorTemperatureTransition#YeelightColorTemperatureTransition(int, int, int) . Brightness is 100
     * @param colorTemp Color temperature value [1700;6500]
     * @param duration Transition duration (ms) (&gt;=50)
     */
    public YeelightColorTemperatureTransition(int colorTemp, int duration) {
        this(colorTemp, duration, 100);
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
