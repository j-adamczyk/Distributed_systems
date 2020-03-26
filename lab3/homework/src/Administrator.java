import topic.TopicListener;
import topic.TopicWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeoutException;

public class Administrator
{
    public static void main(String[] args) throws IOException, TimeoutException
    {
        new Thread(new TopicListener("admin", ign -> {})).start();

        Map<String, String> targetToKey = new HashMap<>();
        targetToKey.put("agencies", "space.agencies.#");
        targetToKey.put("carriers", "space.carriers.#");
        targetToKey.put("all", "space.#");

        TopicWriter writer = new TopicWriter();

        System.out.println("ADMINISTRATOR READY");
        System.out.print("State your messages in the format: ");
        System.out.println("target:msg (target: agencies/carriers/all)\n");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
                System.out.println("Incorrect message format! Should be: target:msg");
                continue;
            }

            String target = line_parts[0];
            String text = line_parts[1];

            Optional.ofNullable(targetToKey.get(target)).ifPresentOrElse(
                    key ->
                    {
                        String msg = "ADMIN:-1:" + text;
                        try
                        {
                            writer.send(msg, "#");
                            System.out.println("Sent \"" + msg + "\" with key \"" + key + "\"");
                        }
                        catch (IOException e)
                        { e.printStackTrace(); }
                    },
                    () -> System.out.println(
                            "Incorrect message format and/or target; " +
                            "it should be: agencies/carriers/all:text")
            );
        }
    }
}
