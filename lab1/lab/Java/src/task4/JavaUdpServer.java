package task4;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class JavaUdpServer {

    public static void main(String[] args)
    {
        System.out.println("JAVA UDP SERVER");

        int portNumber = 9008;
        try (DatagramSocket socket = new DatagramSocket(portNumber))
        {
            byte[] receiveBuffer = new byte[1024];

            while (true)
            {
                Arrays.fill(receiveBuffer, (byte) 0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String msg = new String(receivePacket.getData());

                // use explicit string marker: [J] (Java) or [P] (Python) at the beginning of the string
                // required format: [T] msg...
                String type;
                char typeChar = msg.charAt(1);
                if (typeChar == 'J')
                    type = "Java";
                else if (typeChar == 'P')
                    type = "Python";
                else
                    type = "unknown";

                msg = msg.substring(4);
                System.out.println("Msg from " + type + " client, text: " + msg);

                Thread.sleep(1000);

                String response = "Pong " + type + " Udp";
                byte[] sendBuffer = response.getBytes();

                InetAddress senderAddress = receivePacket.getAddress();
                int senderPort = receivePacket.getPort();
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, senderAddress, senderPort);
                socket.send(sendPacket);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
