package com.mollin.yapi.command;

import com.google.gson.annotations.Expose;
import com.mollin.yapi.utils.YeelightUtils;

public class YeelightCommand {
    private static int ID_GENERATOR = 1;
    @Expose private int id;
    @Expose private String method;
    @Expose private Object[] params;

    private static int generateId() {
        return ID_GENERATOR++;
    }

    public YeelightCommand(String method, Object... params) {
        this.id = YeelightCommand.generateId();
        this.method = method;
        this.params = params;
    }

    public int getId() {
        return this.id;
    }

    public String toJson() {
        return YeelightUtils.GSON.toJson(this);
    }
}
