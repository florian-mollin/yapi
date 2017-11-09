package com.mollin.yapi.enumeration;

/**
 * Action enumeration for adjust methods. (Direction of the adjustment)
 */
public enum YeelightAdjustAction {
    INCREASE("increase"),
    DECREASE("decrease"),
    CIRCLE("circle");

    /**
     * Representation in request parameters
     */
    private String value;

    YeelightAdjustAction(String value) {
        this.value = value;
    }

    /**
     * Getter for value (ie. representation of adjustment action in request parameters)
     * @return Value for adjustment
     */
    public String getValue() {
        return this.value;
    }
}
