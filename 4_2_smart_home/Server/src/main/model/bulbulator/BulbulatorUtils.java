package main.model.bulbulator;

import IoT.BaseBulbulator;
import IoT.UnknownSettingException;

import java.util.HashMap;
import java.util.Map;

public class BulbulatorUtils
{
    public static Map<String, String> getDeviceData(BaseBulbulator bulbulator)
    {
        Map<String, String> data = new HashMap<>();
        data.put("name", bulbulator.name);
        data.put("serialNumber", bulbulator.serialNumber);
        data.put("deviceType", bulbulator.deviceType);

        data.put("bulRepeatNumber", String.valueOf(bulbulator.bulRepeatNumber));
        return data;
    }

    public static void changeSettings(BaseBulbulator bulbulator, Map<String, String> settings)
            throws UnknownSettingException
    {
        for (Map.Entry<String, String> entry: settings.entrySet())
        {
            switch(entry.getKey())
            {
                case "bulNumber":
                    bulbulator.bulRepeatNumber = Short.parseShort(entry.getValue());
                default:
                    String reason = "Setting \"" + entry.getKey() + "\" is not known or cannot be set for " +
                            bulbulator.deviceType;
                    throw new UnknownSettingException(reason);
            }
        }
    }
}
