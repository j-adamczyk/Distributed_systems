package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ClientHandler implements Runnable
{
    private Server server;
    private Socket socket;

    private String name;
    private Scanner in;
    private PrintWriter out;

    ClientHandler(Server server, Socket socket)
    {
        this.server = server;
        this.socket = socket;
    }

    public void run()
    {
        try
        {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            // request a unique name from client; if we can't get it, abort
            requestName();
            if (this.name == null)
                return;

            // accept this client, inform others, add him to writers
            acceptName();

            // Accept messages from this client and broadcast them.
            while (true)
            {
                try
                {
                    String input = in.nextLine();
                    if (input.toLowerCase().startsWith("/quit"))
                        return;

                    for (PrintWriter writer: server.getWriters())
                        writer.println("MESSAGE " + name + ": " + input);
                }
                catch (NoSuchElementException e)
                { return; }
            }
        }
        catch (IOException e)
        { e.printStackTrace(); }
        finally
        {
            if (out != null)
                server.removeWriter(out);

            if (name != null)
            {
                System.out.println("Client " + name + " has left");
                server.removeName(name);

                for (PrintWriter writer : server.getWriters())
                    if (writer != this.out)
                        writer.println("MESSAGE " + name + " has left");
            }

            try
            { socket.close(); }
            catch (IOException e)
            { e.printStackTrace(); }
        }
    }

    public void requestName()
    {
        while (true)
        {
            out.println("SUBMITNAME");
            String name = in.nextLine();
            if (name == null)
                return;

            synchronized (server.getNames())
            {
                if (!name.isBlank() && server.isNameAvailable(name))
                {
                    server.addName(name);
                    this.name = name;
                    break;
                }
            }
        }
    }

    public void acceptName()
    {
        System.out.println("Client " + name + " has joined");
        out.println("NAMEACCEPTED " + name);

        synchronized (server.getWriters())
        {
            for (PrintWriter writer: server.getWriters())
                if (writer != this.out)
                    writer.println("MESSAGE " + name + " has joined");

            server.addWriter(out);
        }
    }
}
