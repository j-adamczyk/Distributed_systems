package main.servants.bulbulator;

import IoT.IBulbulator;
import IoT.UnknownSettingException;
import com.zeroc.Ice.Current;
import main.model.bulbulator.Bulbulator;

import java.util.Map;

public class BulbulatorServant implements IBulbulator
{
    private String name;
    private static Bulbulator instance;

    public BulbulatorServant(String name)
    {
        super();
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
            instance = new Bulbulator(this.name);

        return instance.getDeviceData(current);
    }

    public void changeSettings(Map<String, String> settings, Current current)
            throws UnknownSettingException
    {
        if (instance == null)
            instance = new Bulbulator(this.name);

        instance.changeSettings(settings, current);
    }

    public String bulbulbul(Current current)
    {
        if (instance == null)
            instance = new Bulbulator(this.name);

        return instance.bulbulbul(current);
    }
}
