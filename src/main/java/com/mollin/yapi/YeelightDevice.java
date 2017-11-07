package com.mollin.yapi;

import com.mollin.yapi.command.YeelightCommand;
import com.mollin.yapi.enumeration.YeelightAdjustAction;
import com.mollin.yapi.enumeration.YeelightAdjustProperty;
import com.mollin.yapi.enumeration.YeelightEffect;
import com.mollin.yapi.enumeration.YeelightProperty;
import com.mollin.yapi.exception.YeelightResultErrorException;
import com.mollin.yapi.result.YeelightResultError;
import com.mollin.yapi.result.YeelightResultOk;
import com.mollin.yapi.exception.YeelightSocketException;
import com.mollin.yapi.socket.YeelightSocketHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static com.mollin.yapi.utils.YeelightUtils.clamp;

public class YeelightDevice {
    private final YeelightSocketHolder socketHolder;
    private YeelightEffect effect;
    private int duration;

    public YeelightDevice(String ip, int port, YeelightEffect effect, int duration) throws YeelightSocketException {
        this.socketHolder = new YeelightSocketHolder(ip, port);
        this.setEffect(effect);
        this.setDuration(duration);
    }

    public YeelightDevice(String ip, int port) throws YeelightSocketException {
        this(ip, port, YeelightEffect.SUDDEN, 0);
    }

    public YeelightDevice(String ip) throws YeelightSocketException {
        this(ip, 55443);
    }

    public void setEffect(YeelightEffect effect) {
        this.effect = effect == null ? YeelightEffect.SUDDEN : effect;
    }

    public void setDuration(int duration) {
        this.duration = Math.max(0, duration);
    }

    private String[] readUntilResult(int id) throws YeelightSocketException, YeelightResultErrorException {
        do {
            String datas = this.socketHolder.readLine();
            Optional<YeelightResultOk> okResult = YeelightResultOk.from(datas);
            Optional<YeelightResultError> errorResult = YeelightResultError.from(datas);
            if (okResult.isPresent() && okResult.get().getId() == id) {
                return okResult.get().getResult();
            } else if (errorResult.isPresent() && errorResult.get().getId() == id) {
                throw errorResult.get().getException();
            }
        } while (true);
    }

    private String[] sendCommand(YeelightCommand command) throws YeelightSocketException, YeelightResultErrorException {
        String jsonCommand = command.toJson() + "\r\n";
        this.socketHolder.send(jsonCommand);
        return this.readUntilResult(command.getId());
    }

    public Map<YeelightProperty, String> getProperties(YeelightProperty... properties) throws YeelightResultErrorException, YeelightSocketException {
        YeelightProperty[] expectedProperties = properties.length == 0 ? YeelightProperty.values() : properties;
        Object[] expectedPropertiesValues = Stream.of(expectedProperties).map(YeelightProperty::getValue).toArray();
        YeelightCommand command = new YeelightCommand("get_prop", expectedPropertiesValues);
        String[] result = this.sendCommand(command);
        Map<YeelightProperty, String> propertyToValueMap = new HashMap<>();
        for (int i = 0; i < expectedProperties.length; i++) {
            propertyToValueMap.put(expectedProperties[i], result[i]);
        }
        return propertyToValueMap;
    }

    public void setRGB(int r, int g, int b) throws YeelightResultErrorException, YeelightSocketException {
        r = clamp(r, 0, 255);
        g = clamp(g, 0, 255);
        b = clamp(b, 0, 255);
        int rgbValue = r * 65536 + g * 256 + b;
        YeelightCommand command = new YeelightCommand("set_rgb", rgbValue, this.effect.getValue(), this.duration);
        this.sendCommand(command);
    }

    public void setColorTemperature(int colorTemp) throws YeelightResultErrorException, YeelightSocketException {
        colorTemp = clamp(colorTemp, 1700, 6500);
        YeelightCommand command = new YeelightCommand("set_ct_abx", colorTemp, this.effect.getValue(), this.duration);
        this.sendCommand(command);
    }

    public void setHSV(int hue, int sat) throws YeelightResultErrorException, YeelightSocketException {
        hue = clamp(hue, 0, 359);
        sat = clamp(sat, 0, 100);
        YeelightCommand command = new YeelightCommand("set_hsv", hue, sat, this.effect.getValue(), this.duration);
        this.sendCommand(command);
    }

    public void setBrightness(int brightness) throws YeelightResultErrorException, YeelightSocketException {
        brightness = clamp(brightness, 1, 100);
        YeelightCommand command = new YeelightCommand("set_bright", brightness, this.effect.getValue(), this.duration);
        this.sendCommand(command);
    }

    public void setPower(boolean power) throws YeelightResultErrorException, YeelightSocketException {
        String powerStr = power ? "on" : "off";
        YeelightCommand command = new YeelightCommand("set_power", powerStr, this.effect.getValue(), this.duration);
        this.sendCommand(command);
    }

    public void toggle() throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand command = new YeelightCommand("toggle");
        this.sendCommand(command);
    }

    public void setDefault() throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand command = new YeelightCommand("set_default");
        this.sendCommand(command);
    }

    public void addCron(int delay) throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand command = new YeelightCommand("cron_add", 0, delay);
        this.sendCommand(command);
    }

    public int getCronDelay() throws YeelightResultErrorException, YeelightSocketException {
        Map<YeelightProperty, String> propertyToString = this.getProperties(YeelightProperty.DELAY_OFF);
        try {
            return Integer.parseInt(propertyToString.get(YeelightProperty.DELAY_OFF));
        } catch (Exception ex) {
            return 0;
        }
    }

    public void deleteCron() throws YeelightResultErrorException, YeelightSocketException {
        YeelightCommand command = new YeelightCommand("cron_del", 0);
        this.sendCommand(command);
    }

    public void setAdjust(YeelightAdjustProperty property, YeelightAdjustAction action) throws YeelightResultErrorException, YeelightSocketException {
        String actionValue = action == null ? "" : action.getValue();
        String propertyValue = property == null ? "" : property.getValue();
        YeelightCommand command = new YeelightCommand("set_adjust", actionValue, propertyValue);
        this.sendCommand(command);
    }

    public void setName(String name) throws YeelightResultErrorException, YeelightSocketException {
        if (name == null) {
            name = "";
        }
        YeelightCommand command = new YeelightCommand("set_name", name);
        this.sendCommand(command);
    }
}
