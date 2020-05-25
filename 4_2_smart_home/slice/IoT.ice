
#ifndef IOT_ICE
#define IOT_ICE

module IoT
{
    // those should not have string values, but ICE only allows homogenous values
    // those will have to be parsed, e. g. string to float
    dictionary<string, string> DeviceData;
    dictionary<string, string> NewSettings;

    enum CoffeeType
    {
        ESPRESSO, AMERICANO, CAPPUCCINO, LATTE, HOTWATER, COLDWATER,
        HOTMILK, COLDMILK, COLDCOFFEE  // can't use "ICECOFFEE", since ICE prefix is reserved
    };

    sequence<CoffeeType> allowedCoffeeTypes;

    enum MicrowaveMode
    {
        HEATUP, KEEPWARM, DEFROST, GRILL
    };

    sequence<MicrowaveMode> allowedMicrowaveModes;

    struct SingleCoffeeOrder
    {
        CoffeeType coffeeType;
        short temperature;
        short volume;
    };

    sequence<SingleCoffeeOrder> CoffeeOrder;

    struct Coffee
    {
        CoffeeType coffeeType;
        short temperature;
        short volume;
    };

    sequence<Coffee> Coffees;

    exception ValueOutOfRangeException
    { string reason; };

    exception UnknownSettingException
    { string reason; };

    exception UnsupportedCoffeeTypeException
    { string reason; };

    exception UnsupportedMicrowaveModeException
    { string reason; };

    class IoTDevice
    {
        string name;
        string deviceType;
        string serialNumber;
    };

    interface IIoTDevice
    {
        DeviceData getDeviceData();
        idempotent void changeSettings(NewSettings settings)
            throws UnknownSettingException, ValueOutOfRangeException;
    };

    class CoffeeMaker extends IoTDevice
    {
        short temperature;
        short minTemperature;
        short maxTemperature;

        short volume;
        short minVolume;
        short maxVolume;

        allowedCoffeeTypes allowedTypes;
    };

    interface ICoffeeMaker extends IIoTDevice
    {
        Coffee makeCoffee(CoffeeType coffeeType)
            throws UnsupportedCoffeeTypeException;
    };

    interface ISuperCoffeeMaker extends ICoffeeMaker
    {
        idempotent Coffees makeCustomCoffeeOrder(CoffeeOrder order)
            throws UnsupportedCoffeeTypeException, ValueOutOfRangeException;
    };

    class Microwave extends IoTDevice
    {
        short power;
        short minPower;
        short maxPower;

        bool useSound;

        allowedMicrowaveModes allowedModes;
    };

    interface IMicrowave extends IIoTDevice
    {
        // this really should return some food and possibly sound, but this
        // would make the entire program a bit too large
        string microwave(MicrowaveMode mode)
            throws UnsupportedMicrowaveModeException;
        void toggleUsingSound();
    };

    interface IBulbulator extends IIoTDevice
    {
        string bulbulbul();
    };

    class BaseBulbulator extends IoTDevice
    {
        short bulRepeatNumber;  // how many times should it return "bul" in string
    };
};

#endif
