package queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class QueueWriter
{
    private Channel channel;
    private String key;

    public QueueWriter(String key) throws IOException, TimeoutException
    {
        this.key = key;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        channel.queueDeclare(this.key, false, false, false, null);
    }

    public void send(String msg) throws IOException
    {
        channel.basicPublish(
                "",
                key,
                null,
                msg.getBytes());
    }
}
