package com.mollin.yapi.flow.transition;

import com.mollin.yapi.utils.YeelightUtils;

/**
 * Represent a flow transition
 */
public abstract class YeelightTransition {
    /**
     * Clamp brightness between 1 and 100.
     * @param brightness Brightness to clamp
     * @return Clamped brightness
     */
    protected static int clampBrightness(int brightness) {
        return YeelightUtils.clamp(brightness, 1, 100);
    }

    /**
     * Clamp duration between 50 and +infinity
     * @param duration Duration to clamp
     * @return Clamped duration
     */
    protected static int clampDuration(int duration) {
        return Math.max(50, duration);
    }

    /**
     * Getter for duration
     * @return Duration
     */
    public abstract int getDuration();

    /**
     * Getter for mode
     * @return Mode
     */
    public abstract YeelightTransitionMode getMode();

    /**
     * Getter for value
     * @return Value
     */
    public abstract int getValue();

    /**
     * Getter for brightness
     * @return Brightness
     */
    public abstract int getBrightness();

    /**
     * Return tuple for transition (used in command)
     * @return Transition tuple
     */
    public int[] getTuple() {
        return new int[] {
                this.getDuration(),
                this.getMode().getValue(),
                this.getValue(),
                this.getBrightness()
        };
    }

    /**
     * Enumeration for transition mode
     */
    protected enum YeelightTransitionMode {
        COLOR(1),
        COLOR_TEMPERATURE(2),
        SLEEP(7);

        private int value;

        YeelightTransitionMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return this.value;
        }
    }
}
