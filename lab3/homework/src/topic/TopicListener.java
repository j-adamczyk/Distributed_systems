package topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class TopicListener implements Runnable
{
    private final String EXCHANGE_NAME = "space";
    private String key;
    private Consumer<String> msgHandler;

    public TopicListener(String key, Consumer<String> msgHandler)
    {
        this.key = key;
        this.msgHandler = msgHandler;
    }

    @Override
    public void run()
    {
        try
        {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, key);

            com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel)
            {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                {
                    String msg = new String(body, StandardCharsets.UTF_8);
                    System.out.println("Received: \"" + msg + "\"");
                    msgHandler.accept(msg);
                }
            };

            channel.basicConsume(queueName, true, consumer);
        }
        catch (IOException | TimeoutException e)
        { e.printStackTrace(); }
    }
}
