package com.mollin.yapi.exception;

public class YeelightResultErrorException extends Exception {
    private int code;

    public YeelightResultErrorException(int code, String message) {
        super(message + " (" + code + ")");
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
