package ef.client.util;

public class GameStatusHolder
{
    private static String gameStatus;

    public static String getGameStatus(){ return gameStatus; }
    public static void setGameStatus(String gameStatus){ GameStatusHolder.gameStatus = gameStatus; }
}
