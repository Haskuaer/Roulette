package ef.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ef.models.Users;

public class UserDao {

    private SessionFactory sessionFactory;

    public UserDao(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public Users getUserByUsername(String username) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query<Users> query = session.createQuery("from Users where username = :username", Users.class);
        query.setParameter("username", username);
        Users user = query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        return user;
    }

    public Users addUser(Users user) throws IllegalAccessException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try{
            Query<Users> query = session.createQuery("from Users where username = :username", Users.class);
            query.setParameter("username", user.getUsername());
            Users existingUser = query.uniqueResult();

            if(existingUser != null) { throw new IllegalAccessException("User exists"); }

            session.save(user);

            session.getTransaction().commit();
            return user;
        } catch (Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
