package main.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListenerTCP implements Runnable
{
    Server server;
    int serverPort;
    int maxClients;

    ListenerTCP(Server server, int serverPort, int maxClients)
    {
        this.server = server;
        this.serverPort = serverPort;
        this.maxClients = maxClients;
    }

    @Override
    public void run()
    {
        try (ServerSocket serverSocket = new ServerSocket(serverPort))
        {
            System.out.println("Server TCP handler ready.");
            ExecutorService executor = Executors.newFixedThreadPool(maxClients);
            while(!serverSocket.isClosed())
                executor.submit(new ClientHandler(server, serverSocket.accept()));
        }
        catch (IOException e)
        { e.printStackTrace(); }
    }
}
