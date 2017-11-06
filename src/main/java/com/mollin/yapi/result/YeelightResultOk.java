package com.mollin.yapi.result;

import com.google.gson.annotations.Expose;
import com.mollin.yapi.utils.YeelightUtils;

import java.util.Optional;

public class YeelightResultOk {
    @Expose private int id;
    @Expose private String[] result;

    private YeelightResultOk(){
    }

    public static Optional<YeelightResultOk> from(String datas) {
        return YeelightUtils.generateFromJsonDatas(datas, YeelightResultOk::validate, YeelightResultOk.class);
    }

    private static boolean validate(YeelightResultOk okResult) {
        return okResult.result != null;
    }

    public int getId() {
        return this.id;
    }

    public String[] getResult() {
        return this.result;
    }
}
