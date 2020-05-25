package main.model;

import akka.actor.ActorRef;

public class DbRequest
{
    public final ActorRef respondTo;
    public final String product;
    public final int id;

    public DbRequest(ActorRef respondTo, String product, int id)
    {
        this.respondTo = respondTo;
        this.product = product;
        this.id = id;
    }
}
