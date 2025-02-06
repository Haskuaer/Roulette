package ef.dao;
import ef.models.Game;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class GameDao {

    private SessionFactory sessionFactory;

    public GameDao(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public Game startSession()
    {
        Transaction transaction = null;
        Game gameSession = null;

        try(Session session = sessionFactory.openSession())
        {
            transaction = session.beginTransaction();

            gameSession = new Game();
            session.save(gameSession);

            transaction.commit();
            System.out.println("New session created: " + gameSession.getId());
        }
        catch(Exception e)
        {
            if(transaction != null) { transaction.rollback(); }
            e.printStackTrace();
        }
        return gameSession;
    }
}
