package main;

import com.zeroc.Ice.Current;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.ServantLocator;
import main.model.bulbulator.Bulbulator;
import main.model.coffeeMaker.BasicCoffeeMaker;
import main.model.coffeeMaker.ColdCoffeeMaker;
import main.model.coffeeMaker.SuperCoffeeMaker;
import main.model.microwave.BasicMicrowave;
import main.model.microwave.SuperMicrowave;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class CustomServantLocator implements ServantLocator
{
    public List<String> currentlyPresent;
    private ReentrantLock lock;

    public CustomServantLocator()
    {
        this.currentlyPresent = new ArrayList<>();
        this.lock = new ReentrantLock();
    }

    public void printCurrentlyPresent()
    {
        this.lock.lock();
        System.out.println("Currently present devices:");
        for (String name: this.currentlyPresent)
            System.out.println("- " + name);
        System.out.println();
        this.lock.unlock();
    }

    public ServantLocator.LocateResult locate(Current current)
    {
        String name = current.id.name;
        System.out.println("Calling locate with name = " + name);

        ObjectAdapter adapter = current.adapter;

        this.lock.lock();
        // lazy servant creation
        try
        {
            switch (name)
            {
                case "bulbulator1":
                    Bulbulator bulbulator1 = new Bulbulator("bulbulator1");
                    adapter.add(bulbulator1, new Identity(name, "bulbulators"));
                    this.currentlyPresent.add("bulbulator1");
                    return new ServantLocator.LocateResult(bulbulator1, null);
                case "bulbulator2":
                    Bulbulator bulbulator2 = new Bulbulator("bulbulator2");
                    adapter.add(bulbulator2, new Identity(name, "bulbulators"));
                    this.currentlyPresent.add("bulbulator2");
                    return new ServantLocator.LocateResult(bulbulator2, null);
                case "basicCoffeeMaker":
                    BasicCoffeeMaker basicCoffeeMaker = new BasicCoffeeMaker(name);
                    adapter.add(basicCoffeeMaker, new Identity(name, "coffeeMakers"));
                    this.currentlyPresent.add("basicCoffeeMaker");
                    return new ServantLocator.LocateResult(basicCoffeeMaker, null);
                case "coldCoffeeMaker":
                    ColdCoffeeMaker coldCoffeeMaker = new ColdCoffeeMaker(name);
                    adapter.add(coldCoffeeMaker, new Identity(name, "coffeeMakers"));
                    this.currentlyPresent.add("coldCoffeeMaker");
                    return new ServantLocator.LocateResult(coldCoffeeMaker, null);
                case "superCoffeeMaker":
                    SuperCoffeeMaker superCoffeeMaker = new SuperCoffeeMaker(name);
                    adapter.add(superCoffeeMaker, new Identity(name, "coffeeMakers"));
                    this.currentlyPresent.add("superCoffeeMaker");
                    return new ServantLocator.LocateResult(superCoffeeMaker, null);
                case "basicMicrowave1":
                    BasicMicrowave basicMicrowave1 = new BasicMicrowave("basicMicrowave1");
                    adapter.add(basicMicrowave1, new Identity(name, "microwaves"));
                    this.currentlyPresent.add("basicMicrowave1");
                    return new ServantLocator.LocateResult(basicMicrowave1, null);
                case "basicMicrowave2":
                    BasicMicrowave basicMicrowave2 = new BasicMicrowave("basicMicrowave2");
                    adapter.add(basicMicrowave2, new Identity(name, "microwaves"));
                    this.currentlyPresent.add("basicMicrowave2");
                    return new ServantLocator.LocateResult(basicMicrowave2, null);
                case "superMicrowave":
                    SuperMicrowave superMicrowave = new SuperMicrowave(name);
                    adapter.add(superMicrowave, new Identity(name, "microwaves"));
                    this.currentlyPresent.add("superMicrowave");
                    return new ServantLocator.LocateResult(superMicrowave, null);
            }
        }
        finally
        {
            this.lock.unlock();
        }

        // we should NEVER get here
        throw new IllegalStateException("Went too far in ServantLocator!");
    }

    public void finished(Current current, com.zeroc.Ice.Object servant, java.lang.Object cookie)
    { }

    public void deactivate(String category)
    { }
}
