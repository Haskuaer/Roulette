package ef.dao;
import ef.models.GameSession;
import ef.models.Round;
import ef.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class GameSessionDao {

    //DB SESSION
    private SessionFactory sessionFactory;

    //CONSTRUCTOR
    public GameSessionDao(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    //SESSION CREATE
    public GameSession createSession()
    {
        Transaction transaction = null;
        GameSession gameSession = new GameSession();

        try(Session session = sessionFactory.openSession())
        {
            transaction = session.beginTransaction();
            session.merge(gameSession);
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

    //SESSION UPDATE
    public void updateSession(GameSession gameSession)
    {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession())
        {
            transaction = session.beginTransaction();
            session.merge(gameSession);
            transaction.commit();
        }
        catch(Exception e)
        {
            if(transaction != null) { transaction.rollback(); }
            e.printStackTrace();
        }
    }

    //SESSION ADD USER
    public void addUser(User user, GameSession gameSession)
    {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession())
        {
            transaction = session.beginTransaction();
            gameSession.addUser(user);
            gameSession.setPlayersCount(gameSession.getPlayersCount() + 1);
            session.merge(gameSession);
            transaction.commit();
        }
        catch(Exception e)
        {
            if(transaction != null) { transaction.rollback(); }
            e.printStackTrace();
        }
    }

    //SESSION ADD ROUND
    public void addRound(Round round, GameSession gameSession)
    {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession())
        {
            transaction = session.beginTransaction();
            gameSession.addRound(round);
            session.merge(gameSession);
            transaction.commit();
        }
        catch(Exception e)
        {
            if(transaction != null) { transaction.rollback(); }
            e.printStackTrace();
        }
    }

    //CHECK FOR 'waiting' SESSIONS
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
}
