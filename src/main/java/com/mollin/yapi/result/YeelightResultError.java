package com.mollin.yapi.result;

import com.google.gson.annotations.Expose;
import com.mollin.yapi.exception.YeelightResultErrorException;
import com.mollin.yapi.utils.YeelightUtils;

import java.util.Optional;

public class YeelightResultError {
    @Expose private int id;
    @Expose private YeelightErrorResultInfos error;

    private YeelightResultError(){
    }

    public static Optional<YeelightResultError> from(String datas) {
        return YeelightUtils.generateFromJsonDatas(datas, YeelightResultError::validate, YeelightResultError.class);
    }

    private static boolean validate(YeelightResultError errorResult) {
        return errorResult.error != null && errorResult.error.message != null;
    }

    public int getId() {
        return this.id;
    }

    public YeelightResultErrorException getException() {
        return new YeelightResultErrorException(this.error.code, this.error.message);
    }

    private static class YeelightErrorResultInfos {
        @Expose private int code;
        @Expose private String message;
    }
}
