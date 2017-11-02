package com.mollin.yapi;

import com.mollin.yapi.command.YeelightCommand;
import com.mollin.yapi.socket.YeelightSocketException;
import com.mollin.yapi.socket.YeelightSocketHolder;

import static com.mollin.yapi.utils.YeelightUtils.clamp;

public class YeelightDevice {
    private YeelightSocketHolder socketHolder;

    public YeelightDevice(String ip, int port) {
        this.socketHolder = new YeelightSocketHolder(ip, port);
    }

    private boolean sendCommand(YeelightCommand command) {
        String jsonCommand = command.toJson() + "\r\n";
        try {
            this.socketHolder.send(jsonCommand);
            return true;
        } catch (YeelightSocketException e) {
            return false;
        }
    }

    public boolean setRGB(int r, int g, int b) {
        r = clamp(r, 0, 255);
        g = clamp(g, 0, 255);
        b = clamp(b, 0, 255);
        int rgbValue = r * 65536 + g * 256 + b;
        YeelightCommand command = new YeelightCommand("set_rgb", rgbValue);
        return this.sendCommand(command);
    }
}
