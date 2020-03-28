import queue.QueueListener;
import topic.TopicListener;
import topic.TopicWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class Carrier
{
    public static void main(String[] args) throws IOException, TimeoutException
    {
        System.out.println("CARRIER CONFIGURATION");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter carrier ID:");
        String carrierID = br.readLine();

        Set<String> availableServices = new HashSet<>();
        availableServices.add("people");
        availableServices.add("load");
        availableServices.add("satellites");

        String firstService, secondService;
        do
        {
            System.out.println("Enter two services (available: people, load, satellites):");
            firstService = br.readLine();
            secondService = br.readLine();
            System.out.println();
        }
        while (!availableServices.contains(firstService) ||
                !availableServices.contains(secondService) ||
                firstService.equals(secondService));

        System.out.println("CARRIER READY\n");
        TopicWriter writer = new TopicWriter();
        Consumer<String> consumer = msg ->
        {
            String[] split = msg.split(":");
            if (split.length != 3)
                System.out.println("Malformed message received (did not have the form sender:serviceID:data)");
            else if (split[0].equals("ADMIN"))
                System.out.println("ADMIN: " + split[2]);
            else
            {
                String sender = split[0];
                String serviceID = split[1];
                String data = split[2];

                // handle service, do things with data

                String response = carrierID + ":" + serviceID + ":" + "finished service";
                String senderKey = "space.agencies." + sender;
                System.out.println("Sent: \"" + response + "\" to \"" + senderKey + "\"");
                try
                {
                    writer.send(response, senderKey);
                    writer.send(response, "toAdmin");
                }
                catch (IOException e)
                { e.printStackTrace(); }
            }
        };

        new Thread(new QueueListener("space.carriers." + firstService, consumer)).start();
        new Thread(new QueueListener("space.carriers." + secondService, consumer)).start();
        new Thread(new TopicListener("admin.carriers", ign -> {})).start();
        new Thread(new TopicListener("admin.all", ign -> {})).start();
    }
}
