package main.database;

import akka.actor.AbstractActor;
import akka.actor.Props;
import main.model.DbRequest;

public class DbActor extends AbstractActor
{
    @Override
    public Receive createReceive()
    {
        return receiveBuilder()
                .match(DbRequest.class,
                        request -> getContext()
                                .actorOf(Props.create(DbRequestHandlerActor.class))
                                .tell(request, getSender()))
                .matchAny(unknown ->
                        System.err.println("Warning: unrecognized message class \"" + unknown.getClass() +
                                "\" received by DbActor."))
                .build();
    }
}
