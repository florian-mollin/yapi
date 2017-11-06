package com.mollin.yapi.enumeration;

public enum YeelightProperty {
    POWER("power"),
    BRIGHTNESS("bright"),
    COLOR_TEMPERATURE("ct"),
    RGB("rgb"),
    HUE("hue"),
    SAT("sat"),
    COLOR_MODE("color_mode"),
    FLOWING("flowing"),
    DELAY_OFF("delayoff"),
    FLOW_PARAMETERS("flow_params"),
    MUSIC_ON("music_on"),
    NAME("name"),
    BG_POWER("bg_power"),
    BG_FLOWING("bg_flowing"),
    BG_FLOW_PARAMETERS("bg_flow_params"),
    BG_COLOR_TEMPERATURE("bg_ct"),
    BG_COLOR_MODE("bg_lmode"),
    BG_BRIGHTNESS("bg_bright"),
    BG_RGB("bg_rgb"),
    BG_HUE("bg_hue"),
    BG_SAT("bg_sat"),
    NL_BRIGHTNESS("nl_br");

    private String value;

    YeelightProperty(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
