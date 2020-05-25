package main;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.http.javadsl.model.StatusCodes;
import akka.http.javadsl.server.*;
import akka.pattern.Patterns;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;
import main.actors.ServerActor;
import main.model.PriceRequest;
import main.model.PriceResponse;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static akka.http.javadsl.server.Directives.*;
import static akka.http.javadsl.server.PathMatchers.segment;


public class HTTPServer
{
    private final ActorRef server;
    private final ActorSystem actorSystem;
    private final Materializer materializer;

    public HTTPServer(ActorSystem actorSystem, Materializer materializer)
    {
        this.server = actorSystem.actorOf(Props.create(ServerActor.class), "http_server");
        this.actorSystem = actorSystem;
        this.materializer = materializer;
    }

    public static void main(String[] args) throws IOException
    {
        final ActorSystem actorSystem = ActorSystem.create("http");
        final Http http = Http.get(actorSystem);
        final Materializer materializer = Materializer.createMaterializer(actorSystem);

        HTTPServer httpServer = new HTTPServer(actorSystem, materializer);

        final Flow<HttpRequest, HttpResponse, NotUsed> routesFlow = httpServer
                .getRouteHandlers()
                .flow(actorSystem, materializer);

        final CompletionStage<ServerBinding> binding = http
                .bindAndHandle(routesFlow,
                        ConnectHttp.toHost(GlobalConstants.IP, GlobalConstants.PORT),
                        materializer);

        System.out.println("Server ready at " + GlobalConstants.IP + ":" + GlobalConstants.PORT);
        System.in.read(); // let it run until user presses return

        binding.thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> actorSystem.terminate());
    }

    private Route getRouteHandlers()
    {
        final ExceptionHandler exceptionHandler = ExceptionHandler.newBuilder()
                .matchAny(o -> complete(StatusCodes.BAD_REQUEST, "Error: " + o))
                .build();

        final RejectionHandler defaultHandler = RejectionHandler.defaultHandler();

        return concat(
                createPricesRoute(),
                createReviewsRoute()
        ).seal(defaultHandler, exceptionHandler);
    }

    private Route createPricesRoute()
    {
        PathMatcher1<String> matcher = PathMatchers
                .segment("price")
                .slash(segment(Pattern.compile(".*")));

        return path(matcher, product -> get(() -> {
            CompletionStage<Object> reply = Patterns.askWithReplyTo(
                    this.server,
                    actorRef -> new PriceRequest(actorRef, product, -1),
                    Duration.ofSeconds(1));

            return onSuccess(reply, (resp) -> {
                PriceResponse response = (PriceResponse) resp;
                if (response.succeeded)
                    return complete("Best price for " + response.product + " is " + response.price + ".");
                else
                    return complete("No price available at the moment, both server-side actor requests " +
                            "timed out.");
            });
        }));
    }

    private Route createReviewsRoute()
    {
        PathMatcher1<String> matcher = PathMatchers
                .segment("review")
                .slash(segment(Pattern.compile(".*")));

        return path(matcher, product -> get(() -> {
            try
            {
                String url = GlobalConstants.OPINEO_URL_START +
                        URLEncoder.encode(product, StandardCharsets.UTF_8.toString()) +
                        GlobalConstants.OPINEO_URL_END;

                CompletionStage<Object> reply = Http.get(actorSystem)
                        .singleRequest(HttpRequest.create(url))
                        .thenCompose(response ->
                                response.entity().toStrict(GlobalConstants.HTTP_TIMEOUT, materializer))
                        .thenApply(entity -> entity.getData().utf8String())
                        .thenApply(html -> {
                            StringBuilder stringBuilder = new StringBuilder();
                            String firstResult = "No results with positive reviews found.";

                            Pattern pattern = Pattern.compile("Zalety</span>([\\s\\S]*?)</div>");
                            Matcher regexMatcher = pattern.matcher(html);
                            while (regexMatcher.find())
                            {
                                String s = regexMatcher.group(1);
                                if (!s.contains("Brak pozytywnych cech"))
                                {
                                    firstResult = s;
                                    break;
                                }
                            }

                            if (!firstResult.equals("No results with positive reviews found."))
                            {
                                stringBuilder.append("Positive qualities:\n");

                                pattern = Pattern.compile("<li>([\\s\\S]*?)</li>");
                                regexMatcher = pattern.matcher(firstResult);
                                while (regexMatcher.find())
                                {
                                    String s = regexMatcher.group(1);

                                    // "..." indicates end of list
                                    if (s.contains("..."))
                                        break;

                                    stringBuilder.append("- ")
                                            .append(s)
                                            .append("\n");
                                }

                                return stringBuilder.toString();
                            }
                            else
                                return firstResult;
                        });

                return onSuccess(reply, review -> complete(review.toString()));
            }
            catch (UnsupportedEncodingException e)
            {
                return complete("Error occurred during checking encoding of a provided product name.\n" +
                        e.toString());
            }
        }));
    }
}
