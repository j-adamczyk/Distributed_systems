package main;

import grpc.model.ObserveRequest;
import io.grpc.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Client
{
    public static void main(String[] args) throws IOException
    {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        Controller controller = new Controller(channel);

        System.out.println("Client starting work.\n");

        System.out.println("Enter company names to subscribe. " +
                "When you want to finish, enter \"finish\" or provide empty input");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        List<String> companies = new ArrayList<>();
        while (true)
        {
            System.out.print("=> ");
            String input = bufferedReader.readLine();

            if (input.equals("finish") || input.equals(""))
            {
                System.out.println("Finished input, observing now.");
                break;
            }
            else
                companies.add(input);
        }

        List<Thread> threads = new ArrayList<>();
        for (String company: companies)
        {
            ObserveRequest request = ObserveRequest.newBuilder()
                    .setCompanyName(company)
                    .build();

            ClientHandler handler = new ClientHandler(request, controller, channel);
            Thread thread = new Thread(handler);
            threads.add(thread);
            controller.addSubscription(request);
            controller.addThread(thread);
            controller.addHandler(handler);
        }

        for (Thread thread: threads)
            thread.start();
    }

    public static void println(String msg)
    {
        synchronized (System.out)
        {
            System.out.println(msg);
        }
    }

    public static void print(String msg)
    {
        synchronized (System.out)
        {
            System.out.print(msg);
        }
    }
}
