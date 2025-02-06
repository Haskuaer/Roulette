package ef.dao;
import ef.models.GameSession;
import ef.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class GameSessionDao {

    private SessionFactory sessionFactory;

    public GameSessionDao(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public GameSession findAvailableSession()
    {
        try(Session session = sessionFactory.openSession())
        {
            GameSession gameSession = session.createQuery("""
                SELECT gs FROM GameSession gs
                LEFT JOIN FETCH gs.users
                LEFT JOIN FETCH gs.rounds
                WHERE gs.status = :status AND gs.playersCount < gs.maxPlayers
                """, GameSession.class
            )
                    .setParameter("status", "waiting")
                    .setMaxResults(1)
                    .uniqueResult();

            return gameSession;
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
