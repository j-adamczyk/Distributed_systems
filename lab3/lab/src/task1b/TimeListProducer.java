package task1b;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.Arrays;
import java.util.List;

public class TimeListProducer
{
    public static void main(String[] argv) throws Exception
    {
        // info
        System.out.println("TIME LIST PRODUCER");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // queue
        String QUEUE_NAME = "queue1";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // producer (publish msg)
        List<Integer> messages = Arrays.asList(1,5,1,5,1,5,1,5,1,5);

        for (Integer msg: messages)
            channel.basicPublish("",
                    QUEUE_NAME,
                    null,
                    msg.toString().getBytes());

        // close
        channel.close();
        connection.close();
    }
}
