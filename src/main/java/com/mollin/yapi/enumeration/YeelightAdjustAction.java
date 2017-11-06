package com.mollin.yapi.enumeration;

public enum YeelightAdjustAction {
    INCREASE("increase"),
    DECREASE("decrease"),
    CIRCLE("circle");

    private String value;

    YeelightAdjustAction(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
