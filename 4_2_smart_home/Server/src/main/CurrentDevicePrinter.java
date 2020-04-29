package main;

public class CurrentDevicePrinter implements Runnable
{
    private CustomServantLocator servantLocator;

    public CurrentDevicePrinter(CustomServantLocator servantLocator)
    {
        this.servantLocator = servantLocator;
    }

    @Override
    public void run()
    {
        while (true)
        {
            this.servantLocator.printCurrentlyPresent();
            try
            { Thread.sleep(3000); }
            catch (InterruptedException ignored)
            { }
        }
    }
}
