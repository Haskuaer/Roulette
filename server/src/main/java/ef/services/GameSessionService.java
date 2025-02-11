package ef.services;

import ef.dao.GameSessionDao;
import ef.dao.RoundDao;
import ef.dao.UserDao;
import ef.models.GameSession;

public class GameSessionService {
    private final GameSessionDao gameSessionDao;
    private final RoundDao roundDao;
    private final UserDao userDao;

    //CONSTRUCTOR
    public GameSessionService(GameSessionDao gameSessionDao, UserDao userDao, RoundDao roundDao)
    {
        this.gameSessionDao = gameSessionDao;
        this.roundDao = roundDao;
        this.userDao = userDao;
    }

    //MANAGE SESSION JOIN/CREATE
    public GameSession findOrCreateSession()
    {
        GameSession session = gameSessionDao.findAvailableSession();
        if(session == null){ session = gameSessionDao.createSession(); }
        return session;
    }

    public String sessionStatus(GameSession session) { return session.getStatus(); }

    public void setSessionStatus(GameSession session)
    {
        if(session.getMaxPlayers() == 0 && session.getStatus().equals("active")) { session.setStatus("closed"); }
    }
}
