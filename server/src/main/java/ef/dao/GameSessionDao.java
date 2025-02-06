package ef.dao;
import ef.models.GameSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class GameSessionDao {

    private SessionFactory sessionFactory;

    public GameSessionDao(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public GameSession findAvailableSession()
    {
        if (sessionFactory == null) {
            throw new IllegalStateException("SessionFactory is not initialized!");
        }

        try(Session session = sessionFactory.openSession())
        {
            List<GameSession> sessions = session.createQuery(
                    "FROM GameSession WHERE status = 'waiting' AND playersCount < maxPlayers",
                    GameSession.class
            ).getResultList();

            return sessions.isEmpty() ? null : sessions.get(0);
        }
    }

    public GameSession createSession()
    {
        Transaction transaction = null;
        GameSession gameSession = new GameSession();

        try(Session session = sessionFactory.openSession())
        {
            transaction = session.beginTransaction();
            session.save(gameSession);
            transaction.commit();
            System.out.println("Session created: " + gameSession.getId());
        }
        catch(Exception e)
        {
            if(transaction != null) { transaction.rollback(); }
            e.printStackTrace();
        }
        return gameSession;
    }

    public void updateSession(GameSession gameSession)
    {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession())
        {
            transaction = session.beginTransaction();
            session.update(gameSession);
            transaction.commit();
        }
        catch(Exception e)
        {
            if(transaction != null) { transaction.rollback(); }
            e.printStackTrace();
        }
    }
}
