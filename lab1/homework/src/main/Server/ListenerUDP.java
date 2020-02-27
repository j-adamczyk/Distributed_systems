package main.Server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ListenerUDP implements Runnable
{
    Server server;
    int serverPort;

    ListenerUDP(Server server, int serverPort)
    {
        this.server = server;
        this.serverPort = serverPort;
    }

    @Override
    public void run()
    {
        try (DatagramSocket datagramSocket = new DatagramSocket(serverPort))
        {
            System.out.println("Server UDP handler ready.");
            while (!datagramSocket.isClosed())
            {
                byte[] bytes = new byte[1024];
                DatagramPacket datagramPacket = new DatagramPacket(bytes, bytes.length);
                datagramSocket.receive(datagramPacket);

                String msg = new String(bytes);
                String[] split = msg.split(":", 2);
                String name = split[0];

                sendToAll(datagramSocket, msg, name);
            }
        }
        catch (IOException e)
        { e.printStackTrace(); }
    }

    private void sendToAll(DatagramSocket datagramSocket, String msg, String sourceName)
    {
        for (ClientHandler clientHandler: server.getClientHandlers())
            if (!clientHandler.getName().equals(sourceName))
            {
                try
                {
                    int port = clientHandler.getSocket().getPort();
                    InetAddress address = InetAddress.getByName("localhost");

                    byte[] msgBytes = msg.getBytes();
                    datagramSocket.send(new DatagramPacket(msgBytes, msgBytes.length, address, port));
                }
                catch (Exception e)
                { e.printStackTrace(); }
            }
    }
}
