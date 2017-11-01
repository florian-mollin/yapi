package com.mollin.yapi.command;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class YeelightCommand {
    private static int ID_GENERATOR = 1;
    private static Gson GSON = YeelightCommand.createGson();
    @Expose private int id;
    @Expose private String method;
    @Expose private Object[] params;

    private static int generateId() {
        return ID_GENERATOR++;
    }

    private static Gson createGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }

    public YeelightCommand(String method, Object... params) {
        this.id = YeelightCommand.generateId();
        this.method = method;
        this.params = params;
    }

    public String toJson() {
        return YeelightCommand.GSON.toJson(this);
    }
}
