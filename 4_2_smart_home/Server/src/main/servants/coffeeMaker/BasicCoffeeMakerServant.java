package main.servants.coffeeMaker;

import IoT.*;
import com.zeroc.Ice.Current;
import main.model.coffeeMaker.BasicCoffeeMaker;

import java.util.Map;

public class BasicCoffeeMakerServant implements ICoffeeMaker
{
    private String name;
    private static BasicCoffeeMaker instance;

    public BasicCoffeeMakerServant(String name)
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
            instance = new BasicCoffeeMaker(this.name);

        return instance.getDeviceData(current);
    }

    public void changeSettings(Map<String, String> settings, Current current)
            throws UnknownSettingException, ValueOutOfRangeException
    {
        if (instance == null)
            instance = new BasicCoffeeMaker(this.name);

        instance.changeSettings(settings, current);
    }

    public Coffee makeCoffee(CoffeeType coffeeType, Current current)
            throws UnsupportedCoffeeTypeException
    {
        if (instance == null)
            instance = new BasicCoffeeMaker(this.name);

        return instance.makeCoffee(coffeeType, current);
    }
}
