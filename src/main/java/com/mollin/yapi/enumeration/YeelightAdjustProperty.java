package com.mollin.yapi.enumeration;

public enum YeelightAdjustProperty {
    BRIGHTNESS("bright"),
    COLOR_TEMPERATURE("ct"),
    COLOR("color");

    private String value;

    YeelightAdjustProperty(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
