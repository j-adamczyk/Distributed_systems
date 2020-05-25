package main.actors;

import akka.actor.AbstractActor;
import main.model.ClientRequest;
import main.model.PriceRequest;
import main.model.PriceResponse;

public class ClientActor extends AbstractActor
{
    @Override
    public Receive createReceive()
    {
        return receiveBuilder()
            .match(ClientRequest.class,
                    request -> request.server.tell(
                            new PriceRequest(getSelf(), request.product, 0), getSelf()
                    ))
            .match(PriceResponse.class, response ->
                    {
                        if (response.succeeded)
                            System.out.println("Best price for " + response.product + " is " + response.price + ".");
                        else
                            System.out.println("No price available at the moment, both server-side actor requests " +
                                    "timed out.");

                        System.out.println("Request for this product has been made " + response.prevRequests +
                                " times before.");
                    })
            .matchAny(unknown ->
                    System.err.println("Warning: unrecognized message class \"" + unknown.getClass() +
                            "\" received by ClientActor."))
            .build();
    }
}
