package main;

import grpc.model.*;
import io.grpc.Channel;

import java.util.Iterator;

public class ClientHandler implements Runnable
{
    private final StockExchangeInformatorGrpc.StockExchangeInformatorBlockingStub blockingStub;
    private Controller controller;
    private ObserveRequest request;

    @Override
    public void run()
    {
        Iterator<StockInfo> stockInfoIterator = this.blockingStub.observe(this.request);
        try
        {
            while (controller.runHandlers)
            {
                StockInfo stockInfo = stockInfoIterator.next();

                StringBuilder stringBuilder = new StringBuilder();

                stringBuilder.append("Company: ")
                        .append(stockInfo.getCompanyName())
                        .append("\n");

                stringBuilder.append("Time: ")
                        .append(stockInfo.getTimestamp())
                        .append("\n");

                stringBuilder.append("Today spreads (value, change from last):\n");
                for (grpc.model.Spread spread : stockInfo.getSpreadsList())
                    stringBuilder.append("(")
                            .append(spread.getValue())
                            .append(", ")
                            .append(spread.getChangeFromLast())
                            .append("), ");

                stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                stringBuilder.deleteCharAt(stringBuilder.length() - 2);
                stringBuilder.append("\n");

                stringBuilder.append("Last spread: ")
                        .append(stockInfo.getLastSpread().getValue())
                        .append(", ")
                        .append(stockInfo.getLastSpread().getChangeFromLast())
                        .append("\n");

                stringBuilder.append("Transactions today: ")
                        .append(stockInfo.getTransactions())
                        .append("\n");

                stringBuilder.append("Stock status: ")
                        .append(stockInfo.getStockStatus())
                        .append("\n\n");

                Client.println(stringBuilder.toString());
            }
        }
        catch (Exception e)
        {
            if (!this.controller.reconnecting)
                this.controller.reportError();
        }
    }

    public ClientHandler(ObserveRequest request, Controller controller, Channel channel)
    {
        this.blockingStub = StockExchangeInformatorGrpc.newBlockingStub(channel);
        this.controller = controller;
        this.request = request;
    }
}
