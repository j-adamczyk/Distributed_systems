package main.model.bulbulator;

import IoT.BaseBulbulator;
import IoT.IBulbulator;
import IoT.UnknownSettingException;
import com.zeroc.Ice.Current;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

public class Bulbulator extends BaseBulbulator implements IBulbulator
{
    private final String name;
    private final String serialNumber;
    private final String deviceType;

    private short bulRepeatNumber;

    private final ReentrantLock lock;

    public Bulbulator(String name)
    {
        this.name = name;
        this.serialNumber = UUID.randomUUID().toString();
        this.deviceType = this.getClass().getSimpleName();

        this.bulRepeatNumber = 3;

        this.lock = new ReentrantLock();
    }

    @Override
    public Map<String, String> getDeviceData(Current current)
    {
        this.lock.lock();
        Map<String, String> data = BulbulatorUtils.getDeviceData(this);
        this.lock.unlock();
        return data;
    }

    @Override
    public void changeSettings(Map<String, String> settings, Current current)
            throws UnknownSettingException
    {
        this.lock.lock();
        BulbulatorUtils.changeSettings(this, settings);
        this.lock.unlock();
    }

    @Override
    public String bulbulbul(Current current)
    {
        this.lock.lock();
        String bulbulbul = "bul ".repeat(Math.max(0, this.bulRepeatNumber));
        this.lock.unlock();
        return bulbulbul;
    }
}
