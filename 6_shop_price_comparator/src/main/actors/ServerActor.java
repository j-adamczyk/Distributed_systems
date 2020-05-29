package main.actors;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import main.GlobalConstants;
import main.database.DbActor;
import main.model.*;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static akka.actor.SupervisorStrategy.resume;

public class ServerActor extends AbstractActor
{
    private Integer nextId;
    private final Map<Integer, ServerResponseData> activeRequests;

    ServerActor()
    {
        this.nextId = 1;
        this.activeRequests = new HashMap<>();
    }

    @Override
    public SupervisorStrategy supervisorStrategy()
    {
        return new OneForOneStrategy(
                10,
                Duration.ofSeconds(30),
                DeciderBuilder.matchAny(any -> resume())
                .build()
        );
    }

    @Override
    public Receive createReceive()
    {
        return receiveBuilder()
                .match(PriceRequest.class, request ->
                {
                    // request received from client, make requests to other actors

                    String product = request.product;
                    Integer id = this.nextId;
                    this.nextId += 1;

                    PriceRequest serverRequest = new PriceRequest(getSelf(), product, id);
                    ServerResponseData newData = new ServerResponseData(request.respondTo, product);
                    this.activeRequests.put(id, newData);

                    ActorRef shop1 = getContext().actorOf(Props.create(PriceActor.class), "shop" + id + "_1");
                    ActorRef shop2 = getContext().actorOf(Props.create(PriceActor.class), "shop" + id + "_2");
                    shop1.tell(serverRequest, getSender());
                    shop2.tell(serverRequest, getSender());

                    ActorRef dbActor = getContext().actorOf(Props.create(DbActor.class), "db" + id);
                    DbRequest dbRequest = new DbRequest(getSelf(), product, id);
                    dbActor.tell(dbRequest, getSelf());

                    getContext()
                            .system()
                            .scheduler()
                            .scheduleOnce(
                                    Duration.ofMillis(GlobalConstants.TIMEOUT),
                                    getSelf(),
                                    new FinishProcessing(id),
                                    getContext().getSystem().dispatcher(),
                                    ActorRef.noSender()
                            );
                })
                .match(PriceResponse.class, response -> {
                    // response received from one of the shop actors

                    Integer id = response.id;

                    // if the request timed out, the key will not be present in the map, since request will not be
                    // active any more
                    if (!this.activeRequests.containsKey(id))
                        return;

                    ServerResponseData data = this.activeRequests.get(id);
                    data.prices.add(response.price);

                    if (data.prices.size() == 2)
                    {
                        float bestPrice = Math.min(data.prices.get(0), data.prices.get(1));
                        String product = data.product;
                        PriceResponse serverResponse = new PriceResponse(true, bestPrice, product);

                        data.respondTo.tell(serverResponse, getSelf());
                        this.activeRequests.remove(id);
                    }
                })
                .match(DbResponse.class, response -> {
                    // response received from database

                    Integer id = response.id;

                    // if the request timed out, the key will not be present in the map, since request will not be
                    // active any more
                    if (!this.activeRequests.containsKey(id))
                        return;

                    this.activeRequests.get(id).prevRequests = response.prevRequests;
                })
                .match(FinishProcessing.class, finish -> {
                    // max time reached

                    Integer id = finish.id;

                    // if the request was finished before max time, it won't be active and key won't be in the map
                    if (!this.activeRequests.containsKey(id))
                        return;

                    ServerResponseData data = this.activeRequests.get(id);

                    PriceResponse serverResponse;
                    if (data.prices.size() == 0)
                    {
                        // we got no responses from actor helpers, report failure
                        serverResponse = new PriceResponse(false, 0, data.product);
                    }
                    else if (data.prices.size() == 1)
                    {
                        // partial success - we only got 1 response, the late one will be ignored later
                        serverResponse = new PriceResponse(true, data.prices.get(0), data.product);
                    }
                    else
                    {
                        // success - we have both responses
                        float bestPrice = Math.min(data.prices.get(0), data.prices.get(1));
                        serverResponse = new PriceResponse(true, bestPrice, data.product);
                    }

                    this.activeRequests.remove(id);

                    serverResponse.prevRequests = data.prevRequests;
                    data.respondTo.tell(serverResponse, getSelf());
                })
                .matchAny(unknown ->
                        System.err.println("Warning: unrecognized message class \"" + unknown.getClass() +
                                "\" received by ServerActor.\n"))
                .build();
    }
}
