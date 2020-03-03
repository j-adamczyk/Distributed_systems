package main.Client;

import javax.swing.*;
import java.io.PrintWriter;
import java.util.Scanner;

public class ListenerTCP implements Runnable
{
    private Scanner input;
    private PrintWriter output;

    Client client;

    ListenerTCP(Scanner input, PrintWriter output, Client client)
    {
        this.input = input;
        this.output = output;

        this.client = client;
    }

    @Override
    public void run()
    {
        while (input.hasNextLine())
        {
            String line = input.nextLine();
            // server requests registering a name, get and propose a name
            if (line.startsWith("SUBMITNAME"))
                output.println(getName());

            // server accepted proposed name
            else if (line.startsWith("NAMEACCEPTED"))
            {
                String name = line.substring(13);
                client.setName(name);
                client.getFrame().setTitle("Chat - " + name);
                client.getTextField().setEditable(true);
            }

            // regular message
            else if (line.startsWith("MESSAGE"))
                client.showMessage(line.substring(8) + "\n");
        }
    }

    private String getName()
    {
        return JOptionPane.showInputDialog(
                client.getFrame(),
                "Choose a screen name:",
                "Screen name selection",
                JOptionPane.PLAIN_MESSAGE);
    }
}
