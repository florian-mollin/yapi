package com.mollin.yapi.enumeration;

public enum YeelightEffect {
    SMOOTH("smooth"),
    SUDDEN("sudden");

    private String value;

    YeelightEffect(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
