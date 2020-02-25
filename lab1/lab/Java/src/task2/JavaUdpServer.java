package task2;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
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
                System.out.println("received msg: " + msg);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
