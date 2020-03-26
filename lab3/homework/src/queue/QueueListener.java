package queue;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class QueueListener implements Runnable
{
    private String key;
    private Consumer<String> msgHandler;

    public QueueListener(String key, Consumer<String> msgHandler)
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

            channel.queueDeclare(key, false, false, false, null);
            channel.basicQos(1);

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

            channel.basicConsume(key, true, consumer);
        }
        catch (IOException | TimeoutException e)
        { e.printStackTrace(); }
    }
}
