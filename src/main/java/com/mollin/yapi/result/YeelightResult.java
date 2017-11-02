package com.mollin.yapi.result;

import com.google.gson.Gson;

public class YeelightResult {
    private static Gson GSON = new Gson();
    private Integer id = null;
    private boolean error = true;

    private static class YeelightResultModel {
        Integer id;
        Object result;
        Object error;
    }

    private YeelightResult(Integer id, boolean error) {
        this.id = id;
        this.error = error;
    }

    public static YeelightResult from(String datas) {
        try {
            YeelightResultModel model = YeelightResult.GSON.fromJson(datas, YeelightResultModel.class);
            if (model.error != null) {
                return new YeelightResult(model.id, true);
            } else if (model.result != null) {
                return new YeelightResult(model.id, false);
            } else {
                return new YeelightResult(null, true);
            }
        } catch (Exception e) {
            return new YeelightResult(null, true);
        }
    }

    public boolean idEquals(int id) {
        return this.id != null && this.id == id;
    }

    public boolean isError() {
        return this.error;
    }
}
