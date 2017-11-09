package com.mollin.yapi.enumeration;

/**
 * Enumeration for transition effect
 */
public enum YeelightEffect {
    SMOOTH("smooth"),
    SUDDEN("sudden");

    /**
     * Representation in request parameters
     */
    private String value;

    YeelightEffect(String value) {
        this.value = value;
    }

    /**
     * Getter for value (ie. representation of effect in request parameters)
     * @return Value for effect
     */
    public String getValue() {
        return this.value;
    }
}
