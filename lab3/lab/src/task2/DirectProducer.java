package task2;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DirectProducer
{
    public static void main(String[] argv) throws Exception
    {
        // info
        System.out.println("DIRECT PRODUCER");

        // connection & channel
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        // exchange
        String EXCHANGE_NAME = "direct_exchange";
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        while (true)
        {
            // read msg
            System.out.println("MESSAGE");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter routing key: ");
            String key = br.readLine();
            System.out.println("Enter message: ");
            String message = br.readLine();

            // break condition
            if ("exit".equals(message))
                break;

            // publish
            channel.basicPublish(
                    EXCHANGE_NAME,
                    key,
                    null,
                    message.getBytes(StandardCharsets.UTF_8));
            System.out.println("Sent: " + message + "\n");
        }
    }
}
