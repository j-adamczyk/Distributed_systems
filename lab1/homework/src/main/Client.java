package main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    private String serverAddress;
    private int serverPort;

    private String name;
    private Scanner in;
    private PrintWriter out;

    JFrame frame = new JFrame("Chat");
    JTextField textField = new JTextField(50);
    JTextArea messageArea = new JTextArea(16, 50);

    Client(String serverAddress, int serverPort)
    {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;

        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(textField, BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
        frame.pack();

        textField.addActionListener(e ->
        {
            String text = textField.getText();
            messageArea.append(this.name + ": " + text + "\n");
            out.println(text);
            textField.setText("");
        });
    }

    private String getName()
    {
        return JOptionPane.showInputDialog(frame, "Choose a screen name:", "Screen name selection",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void run()
    {
        try
        {
            Socket socket = new Socket(this.serverAddress, this.serverPort);
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream(), true);

            while (in.hasNextLine())
            {
                String line = in.nextLine();
                if (line.startsWith("SUBMITNAME"))
                {
                    out.println(getName());
                }
                else if (line.startsWith("NAMEACCEPTED"))
                {
                    this.name = line.substring(13);
                    this.frame.setTitle("Chat - " + name);
                    textField.setEditable(true);
                }
                else if (line.startsWith("MESSAGE"))
                    messageArea.append(line.substring(8) + "\n");
            }
        }
        catch (IOException e)
        { e.printStackTrace(); }
        finally
        {
            frame.setVisible(false);
            frame.dispose();
        }
    }

    public static void main(String[] args)
    {
        int serverPort = 12345;
        String serverAddress = "127.0.0.1";
        Client client = new Client(serverAddress, serverPort);
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
        client.run();
    }
}