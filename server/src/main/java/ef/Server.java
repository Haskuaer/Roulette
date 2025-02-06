package ef;

import ef.dao.GameSessionDao;
import ef.dao.UserDao;
import ef.handlers.ClientHandler;
import ef.models.User;
import ef.util.DatabaseConnection;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.SQLException;


public class Server {

    private static UserDao userDao;
    private static GameSessionDao gameSessionDao;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(12345)) {

            System.out.println("Server is running on port 12345...");

            Connection db = DatabaseConnection.getConnection();

            SessionFactory factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();

            userDao = new UserDao(factory);
            gameSessionDao = new GameSessionDao(factory);

//            try{
//                User test = new User("test", "test");
//                userDao.addUser(test);
//                userDao.setBalance(test.getId(), 100.0);
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            }

//            try(PreparedStatement preparedStatement = db.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?);")){
//                preparedStatement.setString(1, "test");
//                preparedStatement.setString(2, "test");
//                preparedStatement.executeUpdate();
//                System.out.println("Data saved to database");
//            } catch (SQLException e){
//                e.printStackTrace();
//            }

            while (true) {
                Socket clientSocket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(clientSocket, userDao, gameSessionDao);
                System.out.println("Client connected: " + clientSocket.getInetAddress());
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}