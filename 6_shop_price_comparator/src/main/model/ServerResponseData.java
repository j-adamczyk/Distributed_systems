package main.model;

import akka.actor.ActorRef;

import java.util.ArrayList;
import java.util.List;

public class ServerResponseData
{
    public final ActorRef respondTo;
    public final List<Float> prices;
    public final String product;
    public int prevRequests;

    public ServerResponseData(ActorRef respondTo, String product)
    {
        this.respondTo = respondTo;
        this.prices = new ArrayList<>();
        this.product = product;
        this.prevRequests = 0;
    }
}
