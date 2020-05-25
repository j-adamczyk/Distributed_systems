package main.model;

public class DbResponse
{
    public final int prevRequests;
    public final int id;

    public DbResponse(int prevRequests, int id)
    {
        this.prevRequests = prevRequests;
        this.id = id;
    }
}
