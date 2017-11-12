package com.mollin.yapi.enumeration;

/**
 * Enumeration for flow action
 */
public enum YeelightFlowAction {
    RECOVER(0),
    STAY(1),
    TURN_OFF(2);

    private int value;

    YeelightFlowAction(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }
}
