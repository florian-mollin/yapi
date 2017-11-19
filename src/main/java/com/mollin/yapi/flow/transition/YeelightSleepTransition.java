package com.mollin.yapi.flow.transition;

/**
 * Represents a sleep transition
 */
public class YeelightSleepTransition extends YeelightTransition {
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
     * Sleep transition constructor
     * @param duration Transition duration (ms) (&gt;=50)
     */
    public YeelightSleepTransition(int duration) {
        this.duration = clampDuration(duration);
        this.mode = YeelightTransitionMode.SLEEP;
        this.value = 0;
        this.brightness = 1;
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
