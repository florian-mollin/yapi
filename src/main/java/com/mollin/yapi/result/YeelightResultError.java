package com.mollin.yapi.result;

import com.google.gson.annotations.Expose;
import com.mollin.yapi.exception.YeelightResultErrorException;
import com.mollin.yapi.utils.YeelightUtils;

import java.util.Optional;

/**
 * Representation of command response (when response contains error(s))
 */
public class YeelightResultError {
    /**
     * Command ID that produced the result
     */
    @Expose private int id;
    /**
     * Error informations (such as code and message)
     */
    @Expose private YeelightErrorResultInfos error;

    /**
     * @see YeelightResultError#from(String)
     */
    private YeelightResultError(){
    }

    /**
     * Generate a {@link YeelightResultError} from datas if datas is a response
     * @param datas JSON representation of error response
     * @return YeelightResultError if datas is a valid JSON representation of error response
     */
    public static Optional<YeelightResultError> from(String datas) {
        return YeelightUtils.generateFromJsonDatas(datas, YeelightResultError::validate, YeelightResultError.class);
    }

    /**
     * Validate an errorResult
     * @param errorResult ErrorResult to validate
     * @return True if errorResult contains a non-null error attribute and a non-null error.message attribute
     */
    private static boolean validate(YeelightResultError errorResult) {
        return errorResult.error != null && errorResult.error.message != null;
    }

    /**
     * Getter for command ID
     * @return Command ID that produced the result
     */
    public int getId() {
        return this.id;
    }

    /**
     * Return associated exception
     * @return ResultError exception (with code and message)
     */
    public YeelightResultErrorException getException() {
        return new YeelightResultErrorException(this.error.code, this.error.message);
    }

    /**
     * Inner class for JSON parser
     */
    private static class YeelightErrorResultInfos {
        @Expose private int code;
        @Expose private String message;
    }
}
