package main.model.coffeeMaker;

import IoT.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoffeeUtils
{
    public static void checkCoffeeType(CoffeeType coffeeType, List<CoffeeType> allowedTypes, String deviceType)
            throws UnsupportedCoffeeTypeException
    {
        if (!allowedTypes.contains(coffeeType))
        {
            String reason = "Coffee type " + coffeeType.name() + "is not supported by a " + deviceType;
            throw new UnsupportedCoffeeTypeException(reason);
        }
    }

    public static void checkTemperature(short newTemperature, short minTemperature, short maxTemperature,
                                        String deviceType)
        throws ValueOutOfRangeException
    {
        if (newTemperature < minTemperature || newTemperature > maxTemperature)
        {
            String reason = "Device " + deviceType + " can't have temperature set to " + newTemperature +
                    ", valid range is [" + minTemperature + ", " + maxTemperature + "]";
            throw new ValueOutOfRangeException(reason);
        }
    }

    public static void checkVolume(short newVolume, short minVolume, short maxVolume, String deviceType)
        throws ValueOutOfRangeException
    {
        if (newVolume < minVolume || newVolume > maxVolume)
        {
            String reason = "Device " + deviceType + " can't have volume set to " + newVolume +
                    ", valid range is [" + minVolume + ", " + maxVolume + "]";
            throw new ValueOutOfRangeException(reason);
        }
    }

    public static Map<String, String> getDeviceData(CoffeeMaker coffeeMaker)
    {
        Map<String, String> data = new HashMap<>();
        data.put("name", coffeeMaker.name);
        data.put("serialNumber", coffeeMaker.serialNumber);
        data.put("deviceType", coffeeMaker.deviceType);

        data.put("temperature", String.valueOf(coffeeMaker.temperature));
        data.put("minTemperature", String.valueOf(coffeeMaker.minTemperature));
        data.put("maxTemperature", String.valueOf(coffeeMaker.maxTemperature));

        data.put("volume", String.valueOf(coffeeMaker.volume));
        data.put("minVolume", String.valueOf(coffeeMaker.minVolume));
        data.put("maxVolume", String.valueOf(coffeeMaker.maxVolume));

        data.put("allowedTypes", Arrays.toString(coffeeMaker.allowedTypes));
        return data;
    }

    public static void changeSettings(CoffeeMaker coffeeMaker, Map<String, String> settings)
            throws ValueOutOfRangeException, UnknownSettingException
    {
        for (Map.Entry<String, String> entry: settings.entrySet())
        {
            switch(entry.getKey())
            {
                case "temperature":
                    short newTemperature = Short.parseShort(entry.getValue());
                    checkTemperature(newTemperature, coffeeMaker.minTemperature,
                            coffeeMaker.maxTemperature, coffeeMaker.deviceType);
                    coffeeMaker.temperature = newTemperature;
                case "volume":
                    short newVolume = Short.parseShort(entry.getValue());
                    checkVolume(newVolume, coffeeMaker.minVolume, coffeeMaker.maxVolume,
                            coffeeMaker.deviceType);
                    coffeeMaker.volume = newVolume;
                default:
                    String reason = "Setting \"" + entry.getKey() + "\" is not known or cannot be set for " +
                            coffeeMaker.deviceType;
                    throw new UnknownSettingException(reason);
            }
        }
    }
}
