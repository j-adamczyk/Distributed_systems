package main.model.microwave;

import IoT.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MicrowaveUtils
{
    public static void checkMicrowaveMode(MicrowaveMode microwaveMode, List<MicrowaveMode> allowedTypes,
                                          String deviceType)
            throws UnsupportedMicrowaveModeException
    {
        if (!allowedTypes.contains(microwaveMode))
        {
            String reason = "Coffee type " + microwaveMode.name() + "is not supported by a " + deviceType;
            throw new UnsupportedMicrowaveModeException(reason);
        }
    }

    public static void checkPower(short newPower, short minPower, short maxPower, String deviceType)
            throws ValueOutOfRangeException
    {
        if (newPower < minPower || newPower > maxPower)
        {
            String reason = "Device " + deviceType + " can't have volume set to " + newPower +
                    ", valid range is [" + minPower + ", " + maxPower + "]";
            throw new ValueOutOfRangeException(reason);
        }
    }

    public static Map<String, String> getDeviceData(Microwave microwave)
    {
        Map<String, String> data = new HashMap<>();
        data.put("name", microwave.name);
        data.put("serialNumber", microwave.serialNumber);
        data.put("deviceType", microwave.deviceType);

        data.put("power", String.valueOf(microwave.power));
        data.put("minPower", String.valueOf(microwave.minPower));
        data.put("maxPower", String.valueOf(microwave.maxPower));

        data.put("useSound", String.valueOf(microwave.useSound));

        data.put("allowedModes", Arrays.toString(microwave.allowedModes));

        return data;
    }

    public static void changeSettings(Microwave microwave, Map<String, String> settings)
            throws ValueOutOfRangeException, UnknownSettingException
    {
        for (Map.Entry<String, String> entry: settings.entrySet())
        {
            switch(entry.getKey())
            {
                case "power":
                    short power = Short.parseShort(entry.getValue());
                    checkPower(power, microwave.minPower, microwave.maxPower, microwave.deviceType);
                case "useSound":
                    microwave.useSound = Boolean.parseBoolean(entry.getValue());
                default:
                    String reason = "Setting \"" + entry.getKey() + "\" is not known or cannot be set for " +
                            microwave.deviceType;
                    throw new UnknownSettingException(reason);
            }
        }
    }
}
