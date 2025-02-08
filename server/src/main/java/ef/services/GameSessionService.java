package ef.services;

import ef.dao.GameSessionDao;
import ef.dao.RoundDao;
import ef.models.GameSession;
import ef.models.Round;
import ef.models.User;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameSessionService
{
    private final GameSessionDao gameSessionDao;
    private final RoundDao roundDao;

    public GameSessionService(GameSessionDao gameSessionDao, RoundDao roundDao){ this.gameSessionDao = gameSessionDao; this.roundDao = roundDao; }

    public GameSession findOrCreateSession()
    {
        GameSession session = gameSessionDao.findAvailableSession();
        if(session == null){ session = gameSessionDao.createSession(); }
        return session;
    }

    public String sessionStatus(GameSession session) { return session.getStatus(); }

    public void setSessionStatus(GameSession session)
    {
        if(session.isFull()){ session.setStatus("active"); }
    }

//        if(!session.isFull())
//        {
//            if(session.getRounds().isEmpty()) { Round round = new Round(session); session.addRound(round); }
//            if(session.getPlayersCount() == session.getMaxPlayers()) { session.setStatus("active"); }
//            session.addUser(user);
//            session.getRounds();
//            gameSessionDao.updateSession(session);
//            return true;
//        }
//        return false;
}
