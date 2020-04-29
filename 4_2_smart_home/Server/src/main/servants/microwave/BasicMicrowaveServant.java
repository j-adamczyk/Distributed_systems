package main.servants.microwave;

import IoT.*;
import com.zeroc.Ice.Current;
import main.model.microwave.BasicMicrowave;

import java.util.Map;

public class BasicMicrowaveServant implements IMicrowave
{
    private String name;
    private static BasicMicrowave instance;

    public BasicMicrowaveServant(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Map<String, String> getDeviceData(Current current)
    {
        if (instance == null)
            instance = new BasicMicrowave(this.name);

        return instance.getDeviceData(current);
    }

    public void changeSettings(Map<String, String> settings, Current current)
            throws UnknownSettingException, ValueOutOfRangeException
    {
        if (instance == null)
            instance = new BasicMicrowave(this.name);

        instance.changeSettings(settings, current);
    }

    public String microwave(MicrowaveMode mode, Current current)
            throws UnsupportedMicrowaveModeException
    {
        if (instance == null)
            instance = new BasicMicrowave(this.name);

        return instance.microwave(mode, current);
    }

    public void toggleUsingSound(Current current)
    {
        if (instance == null)
            instance = new BasicMicrowave(this.name);

        instance.toggleUsingSound(current);
    }
}
