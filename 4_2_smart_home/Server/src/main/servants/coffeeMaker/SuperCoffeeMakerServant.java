package main.servants.coffeeMaker;

import IoT.*;
import com.zeroc.Ice.Current;
import main.model.coffeeMaker.SuperCoffeeMaker;

import java.util.Map;

public class SuperCoffeeMakerServant implements ICoffeeMaker
{
    private String name;
    private static SuperCoffeeMaker instance;

    public SuperCoffeeMakerServant(String name)
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
            instance = new SuperCoffeeMaker(this.name);

        return instance.getDeviceData(current);
    }

    public void changeSettings(Map<String, String> settings, Current current)
            throws UnknownSettingException, ValueOutOfRangeException
    {
        if (instance == null)
            instance = new SuperCoffeeMaker(this.name);

        instance.changeSettings(settings, current);
    }

    public Coffee makeCoffee(CoffeeType coffeeType, Current current)
            throws UnsupportedCoffeeTypeException
    {
        if (instance == null)
            instance = new SuperCoffeeMaker(this.name);

        return instance.makeCoffee(coffeeType, current);
    }

    public Coffee[] makeCustomCoffeeOrder(SingleCoffeeOrder[] order, Current current)
            throws UnsupportedCoffeeTypeException, ValueOutOfRangeException
    {
        if (instance == null)
            instance = new SuperCoffeeMaker(this.name);

        return instance.makeCustomCoffeeOrder(order, current);
    }
}
