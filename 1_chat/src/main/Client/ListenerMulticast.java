package main.Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class ListenerMulticast implements Runnable
{
    private MulticastSocket socket;
    private Client client;

    ListenerMulticast(MulticastSocket socket, Client client)
    {
        this.socket = socket;
        this.client = client;
    }

    @Override
    public void run()
    {
        try
        {
            while (!socket.isClosed())
            {
                byte[] buff = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(buff, buff.length);
                socket.receive(datagramPacket);
                String msg = new String(buff);
                if (!msg.startsWith(this.client.getName() + ":"))
                    client.showMessage("(Multicast UDP) " + msg);
            }
        }
        catch (IOException e)
        { e.printStackTrace(); }
    }
}
