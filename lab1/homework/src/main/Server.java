package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;

public class Server
{
    private static Set<String> names = new HashSet<>();
    private static Set<PrintWriter> writers = new HashSet<>();

    public static void main(String[] args) throws IOException
    {
        Server server = new Server();
        server.run();
    }

    public void run() throws IOException
    {
        int maxClients = 20;
        int serverTCPPort = 12345;

        try (var serverSocket = new ServerSocket(serverTCPPort))
        {
            System.out.println("TCP server ready.");
            var pool = Executors.newFixedThreadPool(maxClients);
            while (true)
                pool.execute(new ClientHandler(this, serverSocket.accept()));
        }
    }

    public Set<String> getNames()
    {
        return names;
    }

    public Set<PrintWriter> getWriters()
    {
        return writers;
    }

    public boolean isNameAvailable(String name)
    {
        return !names.contains(name);
    }

    public void addName(String name)
    {
        names.add(name);
    }

    public void removeName(String name)
    {
        names.remove(name);
    }

    public void addWriter(PrintWriter writer)
    {
        writers.add(writer);
    }

    public void removeWriter(PrintWriter writer)
    {
        writers.remove(writer);
    }
}
