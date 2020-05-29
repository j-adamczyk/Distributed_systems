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
                        StringBuilder stringBuilder = new StringBuilder();

                        stringBuilder.append("Actor ")
                                .append(this.getSelf().path().name())
                                .append(" received following response:\n");

                        if (response.succeeded)
                            stringBuilder.append("Best price for ")
                                    .append(response.product)
                                    .append(" is ")
                                    .append(response.price)
                                    .append(".\n");
                        else
                            stringBuilder.append("No price available at the moment, both server-side actor requests " +
                                    "timed out.\n");

                        if (response.prevRequests >= 0)
                            stringBuilder.append("Request for this product has been made ")
                                    .append(response.prevRequests)
                                    .append(" times before.\n");

                        System.out.println(stringBuilder.toString());
                    })
            .matchAny(unknown ->
                    System.err.println("Warning: unrecognized message class \"" + unknown.getClass() +
                            "\" received by ClientActor.\n"))
            .build();
    }
}
