package main.Server;

import java.util.HashSet;
import java.util.Set;

public class Server
{
    private static Set<String> names = new HashSet<>();
    private static Set<ClientHandler> clientHandlers = new HashSet<>();

    public static void main(String[] args)
    {
        Server server = new Server();
        server.run();
    }

    public void run()
    {
        int serverPort = 12345;
        int maxClients = 20;

        ListenerTCP listenerTCP = new ListenerTCP(this, serverPort, maxClients);
        ListenerUDP listenerUDP = new ListenerUDP(this, serverPort);

        new Thread(listenerTCP).start();
        new Thread(listenerUDP).start();
    }

    public synchronized Set<String> getNames()
    {
        return names;
    }

    public synchronized boolean isNameAvailable(String name)
    {
        return !names.contains(name);
    }

    public synchronized void addName(String name)
    {
        names.add(name);
    }

    public synchronized void removeName(String name)
    {
        names.remove(name);
    }

    public synchronized Set<ClientHandler> getClientHandlers()
    {
        return clientHandlers;
    }

    public synchronized void addClientHandler(ClientHandler clientHandler)
    {
        clientHandlers.add(clientHandler);
    }

    public synchronized void removeClientHandler(ClientHandler clientHandler)
    {
        clientHandlers.remove(clientHandler);
    }
}
