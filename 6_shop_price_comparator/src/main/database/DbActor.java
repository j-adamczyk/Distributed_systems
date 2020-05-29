package main.database;

import akka.actor.AbstractActor;
import main.model.DbRequest;
import main.model.DbResponse;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbActor extends AbstractActor
{
    @Override
    public Receive createReceive()
    {
        return receiveBuilder()
                .match(DbRequest.class, request -> {
                    this.saveToDb(request);
                    getContext().stop(getSelf());
                })
                .matchAny(unknown ->
                        System.err.println("Warning: unrecognized message class \"" + unknown.getClass() +
                                "\" received by DbRequestHandlerActor."))
                .build();
    }

    private void saveToDb(DbRequest request) throws ClassNotFoundException, SQLException
    {
        Connection connection = Utils.getDbConnection();
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM history WHERE name = '" + request.product + "';";
        ResultSet result = statement.executeQuery(query);
        int count;

        if (result.isBeforeFirst())
        {
            // row was present in the database
            count = result.getInt("count");
            int newCount = count + 1;
            query = "UPDATE history SET count = " + newCount + " WHERE name='" + request.product + "';";
            statement.executeUpdate(query);
        }
        else
        {
            // this is the first request for that row
            query = "INSERT INTO history (name, count) VALUES ('" + request.product + "', 1);";
            statement.executeUpdate(query);
            count = 0;
        }

        request.respondTo.tell(new DbResponse(count, request.id), getSelf());

        result.close();
        statement.close();
        connection.close();
    }
}
