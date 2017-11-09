package com.mollin.yapi.exception;

/**
 * Thrown when result of device response is wrong
 */
public class YeelightResultErrorException extends Exception {
    /**
     * Error code in response
     */
    private int code;

    /**
     * Constructor for the exception
     * @param code Error code
     * @param message Error message
     */
    public YeelightResultErrorException(int code, String message) {
        super(message + " (" + code + ")");
        this.code = code;
    }

    /**
     * Getter for error code
     * @return Error code
     */
    public int getCode() {
        return this.code;
    }
}
