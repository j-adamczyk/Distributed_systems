package main.model.microwave;

import IoT.*;
import com.zeroc.Ice.Current;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class BasicMicrowave extends Microwave implements IMicrowave
{
    private final String name;
    private final String serialNumber;
    private final String deviceType;

    private short power;
    private final short minPower;
    private final short maxPower;

    private boolean useSound;

    private final List<MicrowaveMode> allowedModes;

    private final ReentrantLock lock;

    public BasicMicrowave(String name)
    {
        this.name = name;
        this.serialNumber = UUID.randomUUID().toString();
        this.deviceType = this.getClass().getSimpleName();

        this.power = 700;
        this.minPower = 300;
        this.maxPower = 800;

        this.useSound = true;

        this.allowedModes = Collections.singletonList(MicrowaveMode.HEATUP);

        this.lock = new ReentrantLock();
    }

    @Override
    public Map<String, String> getDeviceData(Current current)
    {
        this.lock.lock();
        Map<String, String> data = MicrowaveUtils.getDeviceData(this);
        this.lock.unlock();
        return data;
    }

    @Override
    public void changeSettings(Map<String, String> settings, Current current)
            throws UnknownSettingException, ValueOutOfRangeException
    {
        this.lock.lock();
        MicrowaveUtils.changeSettings(this, settings);
        this.lock.unlock();
    }

    @Override
    public String microwave(MicrowaveMode mode, Current current) throws UnsupportedMicrowaveModeException
    {
        this.lock.lock();
        MicrowaveUtils.checkMicrowaveMode(mode, this.allowedModes, this.deviceType);
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("Microwave ")
                .append(this.name)
                .append(" finished preparing food in ")
                .append(mode.toString())
                .append(" mode.");

        if (this.useSound)
            stringBuffer.append(" BEEP");

        this.lock.unlock();
        return stringBuffer.toString();
    }

    @Override
    public void toggleUsingSound(Current current)
    {
        this.lock.lock();
        this.useSound = !this.useSound;
        this.lock.unlock();
    }
}
