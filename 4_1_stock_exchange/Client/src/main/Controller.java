package main;

import grpc.model.*;
import io.grpc.Channel;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class Controller
{
    public ReentrantLock lock;

    private final Channel channel;
    private final StockExchangeInformatorGrpc.StockExchangeInformatorBlockingStub blockingStub;

    public volatile boolean runHandlers;
    public volatile boolean reconnecting;

    private List<ObserveRequest> subscriptions;
    private List<Thread> threads;
    private List<ClientHandler> handlers;

    public Controller(Channel channel)
    {
        this.lock = new ReentrantLock();

        this.channel = channel;
        this.blockingStub = StockExchangeInformatorGrpc.newBlockingStub(channel);

        this.runHandlers = true;
        this.reconnecting = false;

        this.subscriptions = new LinkedList<>();
        this.threads = new ArrayList<>();
        this.handlers = new ArrayList<>();
    }

    public void addSubscription(ObserveRequest request)
    {
        this.subscriptions.add(request);
    }

    public void addThread(Thread thread)
    {
        this.threads.add(thread);
    }

    public void addHandler(ClientHandler handler)
    {
        this.handlers.add(handler);
    }

    public void reportError()
    {
        if (this.lock.tryLock())
        {
            if (!this.reconnecting)
            {
                this.reconnecting = true;
                reconnect();
            }
            this.lock.unlock();
        }
    }

    void reconnect()
    {
        this.lock.lock();
        if (this.subscriptions.isEmpty())
            return;

        Client.println("WARNING: connection error detected, please wait when we fix it.");

        int failedRequests = 1;
        while (failedRequests < 30)
        {
            try
            {
                Thread.sleep(getSleepTime(failedRequests));

                // if connection fails, this will throw an exception
                Empty connect = ping();
            }
            catch (Exception e)
            {
                // we'll get here with failed connect
                failedRequests++;
                Client.print(".");
                continue;
            }
            Client.println("");
            break;
        }

        if (failedRequests == 30)
        {
            System.out.println("\nMajor network error occurred, abandoning work. " +
                    "We are sorry for the inconvenience.\n");
            System.exit(1);
        }

        // if we get here, then we managed to successfully connect

        // restore subscriptions
        this.threads = new ArrayList<>();
        this.handlers = new ArrayList<>();
        for (ObserveRequest request: this.subscriptions)
        {
            ClientHandler handler = new ClientHandler(request, this, this.channel);
            Thread thread = new Thread(handler);
            this.threads.add(thread);
            this.handlers.add(handler);
        }

        this.runHandlers = true;
        this.reconnecting = false;
        Client.println("\nManaged to connect again, restoring subscriptions.\n");
        this.lock.unlock();

        for (Thread thread: threads)
            thread.start();
    }

    private int getSleepTime(int failedRequests)
    {
        if (failedRequests <= 2)
            return 1000;
        else if (failedRequests <= 6)
            return 500;
        else if (failedRequests <= 13)
            return 250;
        else
            return 125;
    }

    public Empty ping()
    {
        return this.blockingStub.ping(Empty.newBuilder().build());
    }
}
