package topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class TopicWriter
{
    private final String EXCHANGE_NAME = "space";
    private final Channel channel;

    public TopicWriter() throws IOException, TimeoutException
    {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        this.channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
    }

    public void send(String msg, String key) throws IOException
    {
        channel.basicPublish(
                EXCHANGE_NAME,
                key,
                null,
                msg.getBytes(StandardCharsets.UTF_8));
    }
}
