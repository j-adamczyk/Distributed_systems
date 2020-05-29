package main;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import main.actors.ClientActor;
import main.actors.ServerActor;
import main.database.Utils;
import main.model.ClientRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


public class Main
{
    public static final ActorSystem actorSystem = ActorSystem.create("local_system");
    private static final ActorRef client = actorSystem.actorOf(Props.create(ClientActor.class), "client");
    private static final ActorRef server = actorSystem.actorOf(Props.create(ServerActor.class), "server");

    public static void simulateManyActors()
    {
        List<String> products = new ArrayList<>(Arrays.asList("coffee", "tea", "laptop", "Akka", "Java"));

        List<ActorRef> clients = new ArrayList<>();
        int client_no = 5;
        for (int i = 0; i < client_no; i++)
            clients.add(actorSystem.actorOf(Props.create(ClientActor.class), "client" + i));

        Random random = new Random();
        for (int i = 0; i < 10; i++)
        {
            String prod1 = products.get(random.nextInt(products.size()));
            String prod2 = products.get(random.nextInt(products.size()));
            String prod3 = products.get(random.nextInt(products.size()));

            ActorRef client1 = clients.get(random.nextInt(clients.size()));
            ActorRef client2 = clients.get(random.nextInt(clients.size()));
            ActorRef client3 = clients.get(random.nextInt(clients.size()));

            client1.tell(new ClientRequest(server, prod1), null);
            client2.tell(new ClientRequest(server, prod2), null);
            client3.tell(new ClientRequest(server, prod3), null);

            try
            { Thread.sleep(1000); }
            catch (InterruptedException e)
            { e.printStackTrace(); }
        }
    }

    public static void main(String[] args)
    {
        Utils.createHistoryTable();

        System.out.println("Starting work, please write your requests below. " +
                "Enter \"simulate\" to simulate many actors simultaneously or \"quit\" to exit.");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while(true)
        {
            try
            {
                String input = bufferedReader.readLine();
                if (input.equals("quit"))
                    break;
                else if (input.equals("simulate"))
                    simulateManyActors();

                client.tell(new ClientRequest(server, input), null);
            }
            catch (IOException e)
            {
                System.err.println("Unexpected error occurred while taking input, ending work.\n");
                actorSystem.terminate();
                System.exit(1);
            }
        }

        actorSystem.terminate();
    }
}
