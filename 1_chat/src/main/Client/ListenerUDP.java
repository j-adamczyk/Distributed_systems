package main.Client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ListenerUDP implements Runnable
{
    private DatagramSocket datagramSocket;
    Client client;

    ListenerUDP(DatagramSocket datagramSocket, Client client)
    {
        this.datagramSocket = datagramSocket;
        this.client = client;
    }

    @Override
    public void run()
    {
        try
        {
            while (!datagramSocket.isClosed())
            {
                byte[] buff = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(buff, buff.length);
                datagramSocket.receive(datagramPacket);
                String msg = new String(buff);
                client.showMessage(msg);
            }
        }
        catch (IOException e)
        { e.printStackTrace(); }
        finally
        {
            if (datagramSocket != null)
                datagramSocket.close();
        }
    }
}
