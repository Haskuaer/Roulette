package ef.services;

import ef.dao.GameSessionDao;
import ef.models.GameSession;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameSessionService
{
    private final GameSessionDao gameSessionDao;

    public GameSessionService(GameSessionDao gameSessionDao){ this.gameSessionDao = gameSessionDao; }

    public GameSession findOrCreateSession()
    {
        GameSession session = gameSessionDao.findAvailableSession();

        if(session == null){ session = gameSessionDao.createSession(); }
        return session;
    }

    public boolean addPlayerToSession(GameSession session)
    {
        if(!session.isFull())
        {
            session.setPlayersCount(session.getPlayersCount() + 1);
            if(session.getPlayersCount() == session.getMaxPlayers()) { session.setStatus("active"); }
            gameSessionDao.updateSession(session);
            return true;
        }
        return false;
    }
}
