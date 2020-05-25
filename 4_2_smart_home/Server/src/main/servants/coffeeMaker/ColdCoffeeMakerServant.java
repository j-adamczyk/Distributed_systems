package main.servants.coffeeMaker;

import IoT.*;
import com.zeroc.Ice.Current;
import main.model.coffeeMaker.ColdCoffeeMaker;

import java.util.Map;

public class ColdCoffeeMakerServant implements ICoffeeMaker
{
    private String name;
    private static ColdCoffeeMaker instance;

    public ColdCoffeeMakerServant(String name)
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
            instance = new ColdCoffeeMaker(this.name);

        return instance.getDeviceData(current);
    }

    public void changeSettings(Map<String, String> settings, Current current)
            throws UnknownSettingException, ValueOutOfRangeException
    {
        if (instance == null)
            instance = new ColdCoffeeMaker(this.name);

        instance.changeSettings(settings, current);
    }

    public Coffee makeCoffee(CoffeeType coffeeType, Current current)
            throws UnsupportedCoffeeTypeException
    {
        if (instance == null)
            instance = new ColdCoffeeMaker(this.name);

        return instance.makeCoffee(coffeeType, current);
    }
}
