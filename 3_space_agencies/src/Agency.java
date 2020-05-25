import queue.QueueWriter;
import topic.TopicListener;
import topic.TopicWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeoutException;

public class Agency
{
    public static void main(String[] args) throws IOException, TimeoutException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("AGENCY CONFIGURATION");
        System.out.println("Enter agency ID:");
        String agencyID = br.readLine();

        new Thread(new TopicListener("space.agencies." + agencyID, ign -> {})).start();
        new Thread(new TopicListener("admin.agencies", ign -> {})).start();
        new Thread(new TopicListener("admin.all", ign -> {})).start();

        TopicWriter adminWriter = new TopicWriter();
        Map<String, QueueWriter> serviceToWriter = new HashMap<>();
        serviceToWriter.put("people", new QueueWriter("space.carriers.people"));
        serviceToWriter.put("load", new QueueWriter("space.carriers.load"));
        serviceToWriter.put("satellites", new QueueWriter("space.carriers.satellites"));

        System.out.println("\nAGENCY READY");
        System.out.print("State your service requests in the format: ");
        System.out.println("serviceType:data (serviceType: people/load/satellites)\n");

        while(true)
        {
            String line = br.readLine();
            if (line.equals("exit"))
            {
                System.out.println("ENDING WORK");
                break;
            }

            String[] line_parts = line.split(":", 2);
            if (line_parts.length != 2)
            {
                System.out.println("Incorrect message format! Should be: serviceType:data");
                continue;
            }

            String serviceType = line_parts[0];
            String data = line_parts[1];

            Optional.ofNullable(serviceToWriter.get(serviceType)).ifPresentOrElse(
                    writer ->
                    {
                        String serviceID = String.valueOf(ThreadLocalRandom.current().nextInt(0, 10000));
                        String msg = agencyID + ":" + serviceID + ":" + data;
                        try
                        {
                            writer.send(msg);
                            adminWriter.send(msg, "admin");
                            System.out.println("Sent: \"" + msg + "\"");
                        }
                        catch (IOException e)
                        { e.printStackTrace(); }
                    },
                    () -> System.out.println(
                            "Incorrect message format and/or serviceType; " +
                                    "it should be: people/load/satellites:data")
            );
        }
    }
}
