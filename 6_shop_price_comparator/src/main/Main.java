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


public class Main
{
    public static final ActorSystem actorSystem = ActorSystem.create("local_system");

    public static void main(String[] args)
    {
        final ActorRef client = actorSystem.actorOf(Props.create(ClientActor.class), "client");
        final ActorRef server = actorSystem.actorOf(Props.create(ServerActor.class), "server");

        Utils.createHistoryTable();

        System.out.println("Starting work, please write your requests below. " +
                "Enter \"quit\" to exit.");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        while(true)
        {
            try
            {
                String input = bufferedReader.readLine();
                if (input.equals("quit"))
                    break;

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
