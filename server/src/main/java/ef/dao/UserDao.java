package ef.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ef.models.User;
import org.hibernate.type.StandardBasicTypes;

import java.util.UUID;

public class UserDao {

    private SessionFactory sessionFactory;

    public UserDao(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }

    public User getUserByUsername(String username) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        Query<User> query = session.createQuery("from User where username = :username", User.class);
        query.setParameter("username", username);
        User user = query.uniqueResult();

        session.getTransaction().commit();
        session.close();

        return user;
    }

    public User addUser(User user) throws IllegalAccessException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try{
            Query<User> query = session.createQuery("from User where username = :username", User.class);
            query.setParameter("username", user.getUsername());
            User existingUser = query.uniqueResult();

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

    public String getUsername(UUID userId) {

        String username = null;

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try{
            Query<String> query = session.createQuery("SELECT username FROM User WHERE id = :id", String.class);
            query.setParameter("id", userId);

            String result = query.uniqueResult();

            if(result != null){ username = result; }
            else { System.out.println("User not found"); }

            session.getTransaction().commit();
        } catch (Exception e) {

            if(session.getTransaction() != null){
                session.getTransaction().rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }

        return username;
    }

    public double getBalance(UUID userId) {

        double balance = 0.0;

        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try{
            Query<Double> query = session.createQuery("SELECT balance FROM User WHERE id = :id", Double.class);
            query.setParameter("id", userId);

            Double result = query.uniqueResult();

            if(result != null){ balance = result; }
            else { System.out.println("User not found"); }

            session.getTransaction().commit();

        } catch (Exception e) {

            if(session.getTransaction() != null){
                session.getTransaction().rollback();
            }
            e.printStackTrace();

        } finally {
            session.close();
        }

        return balance;
    }

    public void setBalance(UUID userId, double amount) throws IllegalAccessException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        try{
            Query<User> query = session.createQuery("from User where id = :id", User.class);
            query.setParameter("id", userId);
            User user = query.uniqueResult();

            if(user == null){
                throw new IllegalAccessException("User not found");
            }

            user.setBalance(user.getBalance() + amount);

            session.update(user);
            session.getTransaction().commit();

        } catch (Exception e){
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
