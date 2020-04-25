package main.model.coffeeMaker;


import IoT.*;
import com.zeroc.Ice.Current;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class BasicCoffeeMaker extends CoffeeMaker implements ICoffeeMaker
{
    private final String name;
    private final String serialNumber;
    private final String deviceType;

    private short temperature;  // degree Celsius
    private final short minTemperature;
    private final short maxTemperature;

    private short volume;  // ml
    private final short minVolume;
    private final short maxVolume;

    private final List<CoffeeType> allowedTypes;

    private final ReentrantLock lock;

    public BasicCoffeeMaker(String name)
    {
        this.name = name;
        this.serialNumber = UUID.randomUUID().toString();
        this.deviceType = this.getClass().getSimpleName();

        this.temperature = 80;
        this.minTemperature = 50;
        this.maxTemperature = 85;

        this.volume = 100;
        this.minVolume = 40;
        this.maxVolume = 300;

        this.allowedTypes = Arrays.asList(
                CoffeeType.ESPRESSO,
                CoffeeType.AMERICANO,
                CoffeeType.CAPPUCCINO,
                CoffeeType.LATTE);

        this.lock = new ReentrantLock();
    }

    @Override
    public Map<String, String> getDeviceData(Current current)
    {
        this.lock.lock();
        Map<String, String> data = CoffeeUtils.getDeviceData(this);
        this.lock.unlock();
        return data;
    }

    @Override
    public void changeSettings(Map<String, String> settings, Current current)
            throws UnknownSettingException, ValueOutOfRangeException
    {
        this.lock.lock();
        CoffeeUtils.changeSettings(this, settings);
        this.lock.unlock();
    }

    @Override
    public Coffee makeCoffee(CoffeeType coffeeType, Current current)
            throws UnsupportedCoffeeTypeException
    {
        this.lock.lock();
        CoffeeUtils.checkCoffeeType(coffeeType, this.allowedTypes, this.deviceType);
        Coffee coffee = new Coffee(coffeeType, this.temperature, this.volume);
        this.lock.unlock();
        return coffee;
    }
}
