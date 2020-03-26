package task1a;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SimpleConsumer
{
    public static void main(String[] argv) throws Exception
    {
        // info
        System.out.println("SIMPLE CONSUMER");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // queue
        String QUEUE_NAME = "queue1";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // consumer (handle msg)
        Consumer consumer = new DefaultConsumer(channel)
        {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException
            {
                String message = new String(body, StandardCharsets.UTF_8);
                System.out.println("Start consuming message: " + message);
                int timeToSleep = Integer.parseInt(message);
                try
                { Thread.sleep(timeToSleep * 1000); }
                catch (InterruptedException e)
                { e.printStackTrace(); }
                channel.basicAck(envelope.getDeliveryTag(), false);
                System.out.println("End consuming message: " + message);
            }
        };

        // start listening
        System.out.println("Waiting for messages...");
        //channel.basicConsume(QUEUE_NAME, true, consumer);
        channel.basicConsume(QUEUE_NAME, false, consumer);

        // close
        //channel.close();
        //connection.close();
    }
}
