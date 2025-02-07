package ef.dao;

import ef.models.GameSession;
import ef.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ef.models.Round;

import java.util.UUID;

public class RoundDao {

    //DB SESSION
    private SessionFactory sessionFactory;

    //CONSTRUCTOR
    public RoundDao(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    //ROUND CREATE
    public Round createRound(GameSession gameSession)
    {
        Transaction transaction = null;
        Round round = new Round();

        try(Session session = sessionFactory.openSession())
        {
            transaction = session.beginTransaction();

            session.merge(round);
            transaction.commit();
            System.out.println("Round created: " + round.getId());
        }
        catch(Exception e)
        {
            if(transaction != null) { transaction.rollback(); }
            e.printStackTrace();
        }
        return round;
    }

    //ROUND ADD USER
    public void addUser(User user, Round round)
    {
        Transaction transaction = null;

        try(Session session = sessionFactory.openSession())
        {
            transaction = session.beginTransaction();

            round.addUser(user);
            user.setRound(round);

            session.merge(round);
            session.merge(user);

            transaction.commit();
        }
        catch (Exception e)
        {
            if (transaction != null) { transaction.rollback(); }
            e.printStackTrace();
        }
    }

    //ROUND UPDATE
    public void updateRound(Round round)
    {
        Transaction transaction = null;
        try(Session session = sessionFactory.openSession())
        {
            transaction = session.beginTransaction();
            session.update(round);
            transaction.commit();
        }
        catch(Exception e)
        {
            if(transaction != null) { transaction.rollback(); }
            e.printStackTrace();
        }
    }
}
