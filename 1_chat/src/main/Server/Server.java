package main.Server;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server
{
    private static Set<String> names = new HashSet<>();
    private static Lock namesLock = new ReentrantLock();

    private static Set<ClientHandler> clientHandlers = new HashSet<>();
    private static Lock clientHandlersLock = new ReentrantLock();

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

    public synchronized Lock getNamesLock()
    {
        return namesLock;
    }

    public boolean isNameAvailable(String name)
    {
        namesLock.lock();
        try
        {
            return !names.contains(name);
        }
        finally
        {
            namesLock.unlock();
        }
    }

    public void addName(String name)
    {
        namesLock.lock();
        try
        {
            names.add(name);
        }
        finally
        {
            namesLock.unlock();
        }
    }

    public void removeName(String name)
    {
        namesLock.lock();
        try
        {
            names.remove(name);
        }
        finally
        {
            namesLock.unlock();
        }
    }

    public synchronized Set<ClientHandler> getClientHandlers()
    {
        return clientHandlers;
    }

    public synchronized Lock getClientHandlersLock()
    {
        return clientHandlersLock;
    }

    public void addClientHandler(ClientHandler clientHandler)
    {
        clientHandlers.add(clientHandler);
    }

    public void removeClientHandler(ClientHandler clientHandler)
    {
        clientHandlers.remove(clientHandler);
    }
}
