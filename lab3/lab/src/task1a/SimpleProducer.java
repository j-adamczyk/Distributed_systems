package task1a;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SimpleProducer
{
    public static void main(String[] argv) throws Exception
    {
        // info
        System.out.println("SIMPLE PRODUCER");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // queue
        String QUEUE_NAME = "queue1";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // producer (publish msg)
        System.out.println("Enter message (waiting time in seconds, integer):");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String message = br.readLine();

        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("Sent: " + message);

        // close
        channel.close();
        connection.close();
    }
}
