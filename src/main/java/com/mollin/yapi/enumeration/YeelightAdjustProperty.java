package com.mollin.yapi.enumeration;

/**
 * Property enumeration for adjust methods. (Property to adjust)
 */
public enum YeelightAdjustProperty {
    BRIGHTNESS("bright"),
    COLOR_TEMPERATURE("ct"),
    COLOR("color");

    /**
     * Representation in request parameters
     */
    private String value;

    YeelightAdjustProperty(String value) {
        this.value = value;
    }

    /**
     * Getter for value (ie. representation of adjustment property in request parameters)
     * @return Value for property
     */
    public String getValue() {
        return this.value;
    }
}
