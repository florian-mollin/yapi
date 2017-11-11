package com.mollin.yapi;

import com.mollin.yapi.command.YeelightCommand;
import com.mollin.yapi.enumeration.YeelightAdjustAction;
import com.mollin.yapi.enumeration.YeelightAdjustProperty;
import com.mollin.yapi.enumeration.YeelightEffect;
import com.mollin.yapi.enumeration.YeelightProperty;
import com.mollin.yapi.socket.YeelightSocketHolder;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(JUnitParamsRunner.class)
public class YeelightDeviceTest {
    /**
     * Default effect for testing device
     */
    private static final YeelightEffect DEFAULT_DEVICE_EFFECT = YeelightEffect.SUDDEN;
    /**
     * Default effect duration for testing device
     */
    private static final int DEFAULT_DEVICE_DURATION = 0;

    /**
     * Create a mocked testing device with an assertion for validate the sent command
     * @param expectedSentCommand Expected sent command (used by assertion)
     * @param effect Device effect
     * @param duration Device effect duration
     * @return Testing device
     * @throws Exception when error occurs
     */
    private static YeelightDevice createTestingDevice(YeelightCommand expectedSentCommand, YeelightEffect effect, int duration) throws Exception {
        String sendCommandMethodName = "sendCommand";
        // mock socketHolder constructor to do nothing
        PowerMockito.whenNew(YeelightSocketHolder.class).withAnyArguments().then(i -> null);
        // create and spy the device
        YeelightDevice device = PowerMockito.spy(new YeelightDevice("", 0, effect, duration));
        // mock device.sendCommand to return empty result
        PowerMockito.doReturn(new String[]{}).when(device, sendCommandMethodName, Mockito.any());
        // check sent command when 'sendCommand' is called
        PowerMockito.when(device, sendCommandMethodName, Mockito.any()).then(
                invocation -> {
                    YeelightCommand commandArg = invocation.getArgument(0);
                    assertThat(commandArg).isEqualToIgnoringGivenFields(expectedSentCommand, "id");
                    return new String[]{};
                }
        );
        return device;
    }

    /**
     * @see YeelightDeviceTest#createTestingDevice(YeelightCommand, YeelightEffect, int)
     * @param expectedSentCommand Expected sent command (used by assertion)
     * @return Testing device
     * @throws Exception when error occurs
     */
    private static YeelightDevice createTestingDevice(YeelightCommand expectedSentCommand) throws Exception {
        return createTestingDevice(expectedSentCommand, DEFAULT_DEVICE_EFFECT, DEFAULT_DEVICE_DURATION);
    }

    /**
     * Create Yeelight command with given method and params, and then add default effect and duration to params
     * @param method Command method
     * @param params Command params
     * @return Command with default effect and duration added to params
     */
    private static YeelightCommand commandWithDefault(String method, Object... params) {
        List<Object> paramsList = new ArrayList<>(Arrays.asList(params));
        paramsList.add(DEFAULT_DEVICE_EFFECT.getValue());
        paramsList.add(DEFAULT_DEVICE_DURATION);
        return new YeelightCommand(method, paramsList.toArray());
    }

    public Object[] parametersForSetColorTemperatureTest() {
        String method = "set_ct_abx";
        return new Object[][] {
                {-10, commandWithDefault(method, 1700)},
                {500, commandWithDefault(method, 1700)},
                {8000, commandWithDefault(method, 6500)},
                {1700, commandWithDefault(method, 1700)},
                {6500, commandWithDefault(method, 6500)},
                {5042, commandWithDefault(method, 5042)}
        };
    }

    @Test
    @Parameters
    @PrepareForTest(YeelightDevice.class)
    public void setColorTemperatureTest(int colorTemp, YeelightCommand expectedSentCommand) throws Exception {
        YeelightDevice device = createTestingDevice(expectedSentCommand);
        device.setColorTemperature(colorTemp);
    }

    public Object[] parametersForSetRGBTest() {
        String method = "set_rgb";
        return new Object[][] {
                {0, 0, 0, commandWithDefault(method, 0)},
                {4, 8, 15, commandWithDefault(method, 264207)},
                {-7, 23, 999, commandWithDefault(method, 6143)}
        };
    }

    @Test
    @Parameters
    @PrepareForTest(YeelightDevice.class)
    public void setRGBTest(int r, int g, int b, YeelightCommand expectedSentCommand) throws Exception {
        YeelightDevice device = createTestingDevice(expectedSentCommand);
        device.setRGB(r, g, b);
    }

    public Object[] parametersForSetHSVTest() {
        String method = "set_hsv";
        return new Object[][] {
                {-10, 50, commandWithDefault(method, 0, 50)},
                {0, 50, commandWithDefault(method, 0, 50)},
                {359, 50, commandWithDefault(method, 359, 50)},
                {700, 50, commandWithDefault(method, 359, 50)},
                {150, -8, commandWithDefault(method, 150, 0)},
                {150, 0, commandWithDefault(method, 150, 0)},
                {150, 200, commandWithDefault(method, 150, 100)},
                {150, 50, commandWithDefault(method, 150, 50)},
                {-10, 400, commandWithDefault(method, 0, 100)}
        };
    }

    @Test
    @Parameters
    @PrepareForTest(YeelightDevice.class)
    public void setHSVTest(int hue, int sat, YeelightCommand expectedSentCommand) throws Exception {
        YeelightDevice device = createTestingDevice(expectedSentCommand);
        device.setHSV(hue, sat);
    }

    public Object[] parametersForSetBrightnessTest() {
        String method = "set_bright";
        return new Object[][] {
                {-15, commandWithDefault(method, 1)},
                {0, commandWithDefault(method, 1)},
                {1, commandWithDefault(method, 1)},
                {100, commandWithDefault(method, 100)},
                {150, commandWithDefault(method, 100)},
                {75, commandWithDefault(method, 75)}
        };
    }

    @Test
    @Parameters
    @PrepareForTest(YeelightDevice.class)
    public void setBrightnessTest(int brightness, YeelightCommand expectedSentCommand) throws Exception {
        YeelightDevice device = createTestingDevice(expectedSentCommand);
        device.setBrightness(brightness);
    }

    public Object[] parametersForSetPowerTest() {
        String method = "set_power";
        return new Object[][] {
                {true, commandWithDefault(method, "on")},
                {false, commandWithDefault(method, "off")}
        };
    }

    @Test
    @Parameters
    @PrepareForTest(YeelightDevice.class)
    public void setPowerTest(boolean power, YeelightCommand expectedSentCommand) throws Exception {
        YeelightDevice device = createTestingDevice(expectedSentCommand);
        device.setPower(power);
    }

    public Object[] parametersForToggleTest() {
        String method = "toggle";
        return new Object[][] {
                {new YeelightCommand(method)}
        };
    }

    @Test
    @Parameters
    @PrepareForTest(YeelightDevice.class)
    public void toggleTest(YeelightCommand expectedSentCommand) throws Exception {
        YeelightDevice device = createTestingDevice(expectedSentCommand);
        device.toggle();
    }

    public Object[] parametersForSetDefaultTest() {
        String method = "set_default";
        return new Object[][] {
                {new YeelightCommand(method)}
        };
    }

    @Test
    @Parameters
    @PrepareForTest(YeelightDevice.class)
    public void setDefaultTest(YeelightCommand expectedSentCommand) throws Exception {
        YeelightDevice device = createTestingDevice(expectedSentCommand);
        device.setDefault();
    }

    public Object[] parametersForAddCronTest() {
        String method = "cron_add";
        return new Object[][] {
                {-15, new YeelightCommand(method, 0, 0)},
                {0, new YeelightCommand(method, 0, 0)},
                {45, new YeelightCommand(method, 0, 45)}
        };
    }

    @Test
    @Parameters
    @PrepareForTest(YeelightDevice.class)
    public void addCronTest(int delay, YeelightCommand expectedSentCommand) throws Exception {
        YeelightDevice device = createTestingDevice(expectedSentCommand);
        device.addCron(delay);
    }

    public Object[] parametersForDeleteCronTest() {
        String method = "cron_del";
        return new Object[][] {
                {new YeelightCommand(method, 0)}
        };
    }

    @Test
    @Parameters
    @PrepareForTest(YeelightDevice.class)
    public void deleteCronTest(YeelightCommand expectedSentCommand) throws Exception {
        YeelightDevice device = createTestingDevice(expectedSentCommand);
        device.deleteCron();
    }

    public Object[] parametersForSetAdjustTest() {
        String method = "set_adjust";
        return new Object[][] {
                {null, null, new YeelightCommand(method, YeelightAdjustAction.CIRCLE.getValue(), YeelightAdjustProperty.COLOR.getValue())},
                {YeelightAdjustProperty.COLOR, YeelightAdjustAction.CIRCLE, new YeelightCommand(method, YeelightAdjustAction.CIRCLE.getValue(), YeelightAdjustProperty.COLOR.getValue())},
                {YeelightAdjustProperty.BRIGHTNESS, YeelightAdjustAction.INCREASE, new YeelightCommand(method, YeelightAdjustAction.INCREASE.getValue(), YeelightAdjustProperty.BRIGHTNESS.getValue())},
                {YeelightAdjustProperty.COLOR_TEMPERATURE, YeelightAdjustAction.DECREASE, new YeelightCommand(method, YeelightAdjustAction.DECREASE.getValue(), YeelightAdjustProperty.COLOR_TEMPERATURE.getValue())}
        };
    }

    @Test
    @Parameters
    @PrepareForTest(YeelightDevice.class)
    public void setAdjustTest(YeelightAdjustProperty property, YeelightAdjustAction action, YeelightCommand expectedSentCommand) throws Exception {
        YeelightDevice device = createTestingDevice(expectedSentCommand);
        device.setAdjust(property, action);
    }

    public Object[] parametersForSetNameTest() {
        String method = "set_name";
        return new Object[][] {
                {null, new YeelightCommand(method, "")},
                {"", new YeelightCommand(method, "")},
                {"test", new YeelightCommand(method, "test")}
        };
    }

    @Test
    @Parameters
    @PrepareForTest(YeelightDevice.class)
    public void setNameTest(String name, YeelightCommand expectedSentCommand) throws Exception {
        YeelightDevice device = createTestingDevice(expectedSentCommand);
        device.setName(name);
    }
}
