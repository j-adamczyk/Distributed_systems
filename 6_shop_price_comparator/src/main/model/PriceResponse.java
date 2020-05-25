package main.model;

public class PriceResponse
{
    public final boolean succeeded;
    public final float price;
    public final String product;
    public int id;

    public int prevRequests;

    public PriceResponse(boolean succeeded, float price, String product)
    {
        this.succeeded = succeeded;
        this.price = price;
        this.product = product;
    }
}
