package main.database;

import main.GlobalConstants;
import main.Main;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteOpenMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Utils
{
    public static Connection getDbConnection() throws ClassNotFoundException, SQLException
    {
        Class.forName(GlobalConstants.SQLITE_DRIVER);
        SQLiteConfig config = new SQLiteConfig();
        config.setOpenMode(SQLiteOpenMode.FULLMUTEX);
        return DriverManager.getConnection(GlobalConstants.SQLITE_URL, config.toProperties());
    }

    synchronized public static void createHistoryTable()
    {
        try
        {
            Connection connection = getDbConnection();
            Statement statement = connection.createStatement();
            String query = "DROP table IF EXISTS history;" +
                           "CREATE TABLE history" +
                           "(name varchar(256) NOT NULL, " +
                           "count int NOT NULL);";
            statement.executeUpdate(query);
            connection.close();
        }
        catch (ClassNotFoundException e)
        {
            System.err.println("Database class not found, ending work.");
            Main.actorSystem.terminate();
            System.exit(1);
        }
        catch (SQLException e)
        {
            System.err.println("Error while connecting to the database using \"" +
                    GlobalConstants.SQLITE_URL + "\" connection string.\n");
            System.err.println(e.toString());
            Main.actorSystem.terminate();
            System.exit(1);
        }
    }
}
