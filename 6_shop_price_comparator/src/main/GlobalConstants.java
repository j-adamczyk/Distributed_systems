package main;

public class GlobalConstants
{
    public static final String SQLITE_DRIVER = "org.sqlite.JDBC";
    public static final String SQLITE_URL = "jdbc:sqlite:history.db";

    public static final int MIN_SLEEP = 100;
    public static final int MAX_SLEEP = 500;

    public static final float MIN_PRICE = 1;
    public static final float MAX_PRICE = 10;

    public static final int TIMEOUT = 300;

    public static final String IP = "localhost";
    public static final int PORT = 8080;

    public static final String OPINEO_URL_START = "https://www.opineo.pl/?szukaj=";
    public static final String OPINEO_URL_END = "&s=2";

    public static final int HTTP_TIMEOUT = 10000;

    private GlobalConstants()
    {}
}
