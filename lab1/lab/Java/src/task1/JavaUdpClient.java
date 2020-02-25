package task1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

public class JavaUdpClient {

    public static void main(String[] args)
    {
        System.out.println("JAVA UDP CLIENT");

        int portNumber = 9008;
        try (DatagramSocket socket = new DatagramSocket())
        {
            InetAddress address = InetAddress.getByName("localhost");
            byte[] sendBuffer = "Ping Java Udp".getBytes();
            byte[] receiveBuffer = new byte[1024];

            while (true)
            {
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, address, portNumber);
                socket.send(sendPacket);

                Thread.sleep(1000);

                Arrays.fill(receiveBuffer, (byte) 0);
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                socket.receive(receivePacket);
                String msg = new String(receivePacket.getData());
                System.out.println("received msg: " + msg);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
