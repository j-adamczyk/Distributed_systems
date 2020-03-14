package main.Server;

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
    private Scanner input;
    private PrintWriter output;

    ClientHandler(Server server, Socket socket)
    {
        this.server = server;
        this.socket = socket;

        try
        {
            input = new Scanner(socket.getInputStream());
            output = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (IOException e)
        { e.printStackTrace(); }
    }

    @Override
    public void run()
    {
        try
        {
            // request a unique name from client; if we can't get it, abort
            requestName();
            if (this.name == null)
                return;

            // accept this client, inform others, add him to writers
            acceptName();

            // accept messages from this client and broadcast them.
            while (true)
            {
                System.out.println(name + " ready");
                try
                {
                    String input = this.input.nextLine();
                    if (input.toLowerCase().startsWith("/quit"))
                        return;

                    String msg = "MESSAGE " + name + ": " + input;
                    sendToAll(msg);
                }
                catch (NoSuchElementException e)
                { return; }
            }
        }
        finally
        {
            server.removeClientHandler(this);

            if (name != null)
            {
                System.out.println(name + " has left");
                server.removeName(name);

                String msg = "MESSAGE " + name + " has left";
                sendToAll(msg);
            }

            try
            { socket.close(); }
            catch (IOException e)
            { e.printStackTrace(); }
        }
    }

    public String getName()
    {
        return name;
    }

    public PrintWriter getWriter()
    {
        return output;
    }

    public Socket getSocket()
    {
        return socket;
    }

    private void requestName()
    {
        while (true)
        {
            // request a new name
            output.println("SUBMITNAME");
            String name = input.nextLine();
            if (name == null)
                return;

            // check proposed name
            if (!name.isBlank() && server.isNameAvailable(name))
            {
                server.addName(name);
                server.addClientHandler(this);

                this.name = name;
                break;
            }
        }
    }

    private void acceptName()
    {
        System.out.println(name + " has joined");
        output.println("NAMEACCEPTED " + name);

        String msg = "MESSAGE " + name + " has joined";
        sendToAll(msg);
    }

    private void sendToAll(String msg)
    {
        server.getClientHandlersLock().lock();
        try
        {
            for (ClientHandler clientHandler : server.getClientHandlers())
                if (!clientHandler.getName().equals(this.name))
                    clientHandler.getWriter().println(msg);
        }
        finally
        {
            server.getClientHandlersLock().unlock();
        }
    }
}
