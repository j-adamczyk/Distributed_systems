package main.model;

import akka.actor.ActorRef;

public class ClientRequest
{
    public final ActorRef server;
    public final String product;

    public ClientRequest(ActorRef server, String product)
    {
        this.server = server;
        this.product = product;
    }
}
