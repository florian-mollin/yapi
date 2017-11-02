package com.mollin.yapi;

import com.mollin.yapi.command.YeelightCommand;
import com.mollin.yapi.result.YeelightResult;
import com.mollin.yapi.socket.YeelightSocketException;
import com.mollin.yapi.socket.YeelightSocketHolder;

import static com.mollin.yapi.utils.YeelightUtils.clamp;

public class YeelightDevice {
    private static String DEFAULT_EFFECT = "smooth";
    private static int DEFAULT_DURATION = 500;
    private YeelightSocketHolder socketHolder;

    public YeelightDevice(String ip, int port) {
        this.socketHolder = new YeelightSocketHolder(ip, port);
    }

    private YeelightResult readUntilResult(YeelightCommand command) {
        YeelightResult result;
        do {
            result = YeelightResult.from(this.socketHolder.readLine());
        } while (!result.idEquals(command.getId()));
        return result;
    }

    private boolean sendCommand(YeelightCommand command) {
        String jsonCommand = command.toJson() + "\r\n";
        try {
            this.socketHolder.send(jsonCommand);
            YeelightResult result = this.readUntilResult(command);
            return !result.isError();
        } catch (YeelightSocketException e) {
            return false;
        }
    }

    public boolean setRGB(int r, int g, int b) {
        r = clamp(r, 0, 255);
        g = clamp(g, 0, 255);
        b = clamp(b, 0, 255);
        int rgbValue = r * 65536 + g * 256 + b;
        YeelightCommand command = new YeelightCommand("set_rgb", rgbValue, DEFAULT_EFFECT, DEFAULT_DURATION);
        return this.sendCommand(command);
    }

    public boolean setColorTemperature(int colorTemp) {
        colorTemp = clamp(colorTemp, 1700, 6500);
        YeelightCommand command = new YeelightCommand("set_ct_abx", colorTemp, DEFAULT_EFFECT, DEFAULT_DURATION);
        return this.sendCommand(command);
    }

    public boolean setHSV(int hue, int sat) {
        hue = clamp(hue, 0, 359);
        sat = clamp(sat, 0, 100);
        YeelightCommand command = new YeelightCommand("set_hsv", hue, sat, DEFAULT_EFFECT, DEFAULT_DURATION);
        return this.sendCommand(command);
    }

    public boolean setBrightness(int brightness) {
        brightness = clamp(brightness, 1, 100);
        YeelightCommand command = new YeelightCommand("set_bright", brightness, DEFAULT_EFFECT, DEFAULT_DURATION);
        return this.sendCommand(command);
    }

    public boolean setPower(boolean power) {
        String powerStr = power ? "on" : "off";
        YeelightCommand command = new YeelightCommand("set_power", powerStr);
        return this.sendCommand(command);
    }

    public boolean toggle() {
        YeelightCommand command = new YeelightCommand("toggle");
        return this.sendCommand(command);
    }

    public boolean setDefault() {
        YeelightCommand command = new YeelightCommand("set_default");
        return this.sendCommand(command);
    }

    public boolean setName(String name) {
        if (name == null) {
            name = "";
        }
        YeelightCommand command = new YeelightCommand("set_name", name);
        return this.sendCommand(command);
    }
}
