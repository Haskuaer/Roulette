package ef.handlers;

import ef.services.GameSession;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameHandler {

    private static final List<GameSession> activeSessions = new ArrayList<>();
    private static final int MAX_SESSIONS = 5;

    public static synchronized GameSession findOrCreateSession()
    {
        for(GameSession session : activeSessions)
        {
            if(!session.isFull()) { return session; }
        }
        if (activeSessions.size() < MAX_SESSIONS)
        {
            GameSession newSession = new GameSession();
            activeSessions.add(newSession);
            System.out.println("New session created");
            return newSession;
        }
        return null;
    }
}
