package task3;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Arrays;

public class JavaUdpServer {

    public static void main(String[] args)
    {
        System.out.println("JAVA UDP SERVER");

        int portNumber = 9008;
        try (DatagramSocket socket = new DatagramSocket(portNumber))
        {
            byte[] receiveBuffer = new byte[1024];
            byte[] sendBuffer;

            while (true)
            {
                Arrays.fill(receiveBuffer, (byte) 0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);

                ByteBuffer byteBuffer = ByteBuffer.wrap(receiveBuffer);
                byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
                int nb = byteBuffer.getInt();

                sendBuffer = ByteBuffer.allocate(4).putInt(nb + 1).array();

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
