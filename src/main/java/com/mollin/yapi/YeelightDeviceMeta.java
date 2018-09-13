package com.mollin.yapi;

public class YeelightDeviceMeta {
    public String ip;
    public int port;
    public String id;
    public String model;
    public String firmware;
    public String[] capabilities;

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof YeelightDeviceMeta))return false;
        return id.equals(((YeelightDeviceMeta)other).id);
    }
}
