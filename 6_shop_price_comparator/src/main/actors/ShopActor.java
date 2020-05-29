package main.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import main.model.PriceRequest;

public class ShopActor extends AbstractActor
{
    @Override
    public Receive createReceive()
    {
        return receiveBuilder()
                .match(PriceRequest.class,
                        request -> getContext().actorOf(Props.create(PriceActor.class)).tell(request, getSender()))
                .matchAny(unknown ->
                        System.err.println("Warning: unrecognized message class \"" + unknown.getClass() +
                                "\" received by ShopActor.\n"))
                .build();
    }
}
