package main.Client;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Client implements Runnable
{
    private String serverAddress;
    private int serverPort;
    private InetAddress UDPAddress;

    private DatagramSocket datagramSocket;
    private PrintWriter output;

    private String multicastAddress;
    private int multicastPort;
    private InetAddress multicastGroup;
    private MulticastSocket multicastSocket;

    private Lock readyLock;
    private Condition readyCondition;
    private String name;

    private JFrame frame = new JFrame("Chat");
    private JTextField textField = new JTextField(50);
    private JTextArea messageArea = new JTextArea(16, 50);

    Client(String serverAddress, int serverPort, String UDPAddress, String multicastAddress, int multicastPort)
    {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        try
        { this.UDPAddress = InetAddress.getByName(UDPAddress); }
        catch (UnknownHostException e)
        { e.printStackTrace(); }

        this.multicastAddress = multicastAddress;
        this.multicastPort = multicastPort;

        this.readyLock = new ReentrantLock();
        this.readyCondition = readyLock.newCondition();

        textField.setEditable(false);
        messageArea.setEditable(false);
        frame.getContentPane().add(getTextField(), BorderLayout.SOUTH);
        frame.getContentPane().add(new JScrollPane(getMessageArea()), BorderLayout.CENTER);
        frame.pack();
        textField.requestFocus();

        textField.addActionListener(e ->
        {
            String text = textField.getText();
            sendMessage(text);
            textField.setText("");
        });
    }

    public static void main(String[] args)
    {
        String serverAddress = "127.0.0.1";
        int serverPort = 12345;

        String UDPAddress = "127.0.0.1";

        String multicastAddress = "230.1.1.1";
        int multicastPort = 12346;

        Client client = new Client(serverAddress, serverPort, UDPAddress, multicastAddress, multicastPort);
        client.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.getFrame().setVisible(true);
        client.run();
    }

    @Override
    public void run()
    {
        try (Socket socket = new Socket(this.serverAddress, this.serverPort))
        {
            Scanner input = new Scanner(socket.getInputStream());
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            this.output = output;

            ListenerTCP listenerTCP = new ListenerTCP(input, output, this);
            new Thread(listenerTCP).start();

            this.datagramSocket = new DatagramSocket(socket.getLocalPort());

            this.multicastSocket = new MulticastSocket(multicastPort);
            this.multicastGroup = InetAddress.getByName(multicastAddress);
            multicastSocket.joinGroup(multicastGroup);

            ListenerUDP listenerUDP = new ListenerUDP(datagramSocket, this);
            ListenerMulticast listenerMulticast = new ListenerMulticast(multicastSocket, this);
            // wait until we successfully register
            readyLock.lock();
            try
            {
                while (name == null)
                    readyCondition.await();
            }
            finally
            { readyLock.unlock(); }

            messageArea.append("You've joined the chat as " + name + "\n");

            new Thread(listenerUDP).start();
            listenerMulticast.run();
        }
        catch (Exception e)
        { e.printStackTrace(); }
        finally
        {
            if (datagramSocket != null)
                datagramSocket.close();

            if (multicastSocket != null)
            {
                try
                { multicastSocket.leaveGroup(multicastGroup); }
                catch (IOException e)
                { e.printStackTrace(); }
                multicastSocket.close();
            }

            getFrame().setVisible(false);
            getFrame().dispose();
        }
    }

    public JFrame getFrame()
    {
        return frame;
    }

    public void setFrame(JFrame frame)
    {
        this.frame = frame;
    }

    public JTextField getTextField()
    {
        return textField;
    }

    public void setTextField(JTextField textField)
    {
        this.textField = textField;
    }

    public JTextArea getMessageArea()
    {
        return messageArea;
    }

    public void setMessageArea(JTextArea messageArea)
    {
        this.messageArea = messageArea;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
        readyLock.lock();
        try
        { readyCondition.signal(); }
        finally
        { readyLock.unlock(); }
    }

    public synchronized void showMessage(String msg)
    {
        this.messageArea.append(msg + "\n");
    }

    public void sendMessage(String msg)
    {
        // UDP
        if (msg.startsWith("/U") || msg.startsWith("/M"))
        {
            char type = msg.charAt(1);

            msg = msg.substring(3);
            try
            {
                msg = new String(Files.readAllBytes(Paths.get(msg)));
                msg = this.name + ":\n" + msg + "\n";
            }
            catch (IOException e)
            { JOptionPane.showMessageDialog(frame, "Error occured while reading file."); }

            System.out.println(msg);
            byte[] msgBytes = msg.getBytes();

            // UDP unicast
            try
            {
                // UDP unicast
                if (type == 'U')
                {
                    DatagramPacket packet = new DatagramPacket(msgBytes, msgBytes.length, UDPAddress, serverPort);
                    this.datagramSocket.send(packet);
                }
                // UDP multicast
                else
                {
                    DatagramPacket packet = new DatagramPacket(msgBytes, msgBytes.length, multicastGroup, multicastPort);
                    this.datagramSocket.send(packet);
                }
                messageArea.append(msg);
            }
            catch (IOException e)
            { e.printStackTrace(); }
        }
        // TCP
        else
        {
            // sometimes Swing null pointer is happening here for no apparent reason
            // (no variables are nulls, even when exception happens)
            try
            {
                messageArea.append(this.name + ": " + msg + "\n");
                output.println(msg);
            }
            catch (NullPointerException ignored)
            {}
        }
    }
}