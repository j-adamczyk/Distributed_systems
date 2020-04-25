package main.model.coffeeMaker;


import IoT.*;
import com.zeroc.Ice.Current;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class SuperCoffeeMaker extends CoffeeMaker implements ISuperCoffeeMaker
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

    public SuperCoffeeMaker(String name)
    {
        this.name = name;
        this.serialNumber = UUID.randomUUID().toString();
        this.deviceType = this.getClass().getSimpleName();

        this.temperature = 80;
        this.minTemperature = 10;
        this.maxTemperature = 95;

        this.volume = 100;
        this.minVolume = 30;
        this.maxVolume = 500;

        this.allowedTypes = Arrays.asList(
                CoffeeType.ESPRESSO,
                CoffeeType.AMERICANO,
                CoffeeType.CAPPUCCINO,
                CoffeeType.LATTE,
                CoffeeType.COLDWATER,
                CoffeeType.COLDMILK,
                CoffeeType.COLDCOFFEE,
                CoffeeType.HOTWATER,
                CoffeeType.HOTMILK);

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

    private Coffee makeCustomCoffee(SingleCoffeeOrder order)
            throws UnsupportedCoffeeTypeException, ValueOutOfRangeException
    {
        CoffeeType coffeeType = order.coffeeType;
        CoffeeUtils.checkCoffeeType(coffeeType, this.allowedTypes, this.deviceType);

        short temperature = order.temperature;
        CoffeeUtils.checkTemperature(temperature, this.minTemperature, this.maxTemperature, this.deviceType);

        short volume = order.volume;
        CoffeeUtils.checkVolume(volume, this.minVolume, this.maxVolume, this.deviceType);

        return new Coffee(coffeeType, temperature, volume);
    }

    @Override
    public Coffee[] makeCustomCoffeeOrder(SingleCoffeeOrder[] order, Current current)
            throws UnsupportedCoffeeTypeException, ValueOutOfRangeException
    {
        this.lock.lock();
        Coffee[] readyOrder = new Coffee[3];
        for (int i = 0; i < order.length; i++)
            readyOrder[i] = makeCustomCoffee(order[i]);
        this.lock.unlock();
        return readyOrder;
    }
}
