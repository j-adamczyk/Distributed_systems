package main.actors;

import akka.actor.AbstractActor;
import main.GlobalConstants;
import main.model.PriceRequest;
import main.model.PriceResponse;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class PriceActor extends AbstractActor
{
    @Override
    public Receive createReceive()
    {
        return receiveBuilder()
                .match(PriceRequest.class, request ->
                {
                    Random rand = new Random();
                    int sleepTime = GlobalConstants.MIN_SLEEP +
                            rand.nextInt(GlobalConstants.MAX_SLEEP - GlobalConstants.MIN_SLEEP + 1);

                    Thread.sleep(sleepTime);
                    float price = GlobalConstants.MIN_PRICE +
                            rand.nextFloat() * (GlobalConstants.MAX_PRICE - GlobalConstants.MIN_PRICE);

                    // round to 2 decimal places
                    BigDecimal rounder = new BigDecimal(Double.toString(price));
                    rounder = rounder.setScale(2, RoundingMode.HALF_UP);
                    price = rounder.floatValue();

                    PriceResponse response = new PriceResponse(true, price, request.product);
                    response.id = request.id;
                    request.respondTo.tell(response, getSelf());
                    getContext().stop(getSelf());
                })
                .matchAny(unknown ->
                        System.err.println("Warning: unrecognized message class \"" + unknown.getClass() +
                                "\" received by PriceActor.\n"))
                .build();
    }
}
