package main;

import com.zeroc.Ice.*;
import main.servants.bulbulator.*;
import main.servants.coffeeMaker.*;
import main.servants.microwave.*;

import java.lang.Exception;
import java.util.Arrays;

public class Server
{
    public void run(String[] args)
    {
        int status = 0;
        Communicator communicator = null;

        try
        {
            // create communicator and object adapter
            communicator = Util.initialize(args);

            ObjectAdapter adapter = communicator.createObjectAdapter("IoTAdapter");

            // create servants-wrappers (they DON'T CONTAIN real objects YET)
            BulbulatorServant bulbulatorServant1 = new BulbulatorServant("Bulbulator1");
            BulbulatorServant bulbulatorServant2 = new BulbulatorServant("Bulbulator2");

            BasicCoffeeMakerServant basicCoffeeMakerServant = new BasicCoffeeMakerServant("BasicCoffeeMaker1");
            ColdCoffeeMakerServant coldCoffeeMakerServant = new ColdCoffeeMakerServant("ColdCoffeeMaker");
            SuperCoffeeMakerServant superCoffeeMakerServant = new SuperCoffeeMakerServant("SuperCoffeeMaker");

            BasicMicrowaveServant basicMicrowaveServant1 = new BasicMicrowaveServant("BasicMicrowaveServant1");
            BasicMicrowaveServant basicMicrowaveServant2 = new BasicMicrowaveServant("BasicMicrowaveServant2");
            SuperMicrowaveServant superMicrowaveServant = new SuperMicrowaveServant("SuperMicrowaveServant");

            // add wrappers to ASM
            adapter.add(bulbulatorServant1, new Identity("Bulbulator1", "bulbulators"));
            adapter.add(bulbulatorServant2, new Identity("Bulbulator2", "bulbulators"));
            adapter.add(basicCoffeeMakerServant, new Identity("BasicCoffeeMaker", "cofeeMakers"));
            adapter.add(coldCoffeeMakerServant, new Identity("ColdCoffeeMaker", "cofeeMakers"));
            adapter.add(superCoffeeMakerServant, new Identity("SuperCoffeeMaker", "cofeeMakers"));
            adapter.add(basicMicrowaveServant1, new Identity("BasicMicrowaveServant1", "microwaves"));
            adapter.add(basicMicrowaveServant2, new Identity("BasicMicrowaveServant2", "microwaves"));
            adapter.add(superMicrowaveServant, new Identity("SuperMicrowaveServant", "microwaves"));

            // turn adapter on, start processing events
            adapter.activate();

            System.out.println("Entering event processing loop...");
            communicator.waitForShutdown();
        }
        catch (Exception e)
        {
            System.err.println(e.toString());
            status = 1;
        }
        if (communicator != null)
        {
            // cleanup
            try
            { communicator.destroy(); }
            catch (Exception e)
            {
                System.err.println(e.toString());
                status = 1;
            }
        }

        System.exit(status);
    }

    public static void main(String[] args)
    {
        Server server = new Server();
        server.run(args);
    }
}
