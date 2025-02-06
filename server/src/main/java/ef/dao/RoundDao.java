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

    private SessionFactory sessionFactory;

    public RoundDao(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public Round createRound(User user, GameSession gameSession)
    {
        Transaction transaction = null;
        Round round = new Round();

        try(Session session = sessionFactory.openSession())
        {
            transaction = session.beginTransaction();
            session.save(round);
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
}
