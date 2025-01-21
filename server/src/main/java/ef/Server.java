package ef;

import com.fasterxml.jackson.databind.ObjectMapper;
import ef.actions.LoginRequest;
import ef.dao.UserDao;
import ef.models.Users;
import ef.util.DatabaseConnection;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.SQLException;


public class Server {

    private static UserDao userDao;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {

            System.out.println("Server is running on port 12345...");

            Connection db = DatabaseConnection.getConnection();

            SessionFactory factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Users.class)
                    .buildSessionFactory();

//
            userDao = new UserDao(factory);

            try{
                Users test = new Users("test", "test");
                userDao.addUser(test);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

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
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Reading
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
                String receivedJson = in.readLine();
                System.out.println("Received: " + receivedJson);

                //JSON handling
                ObjectMapper objectMapper = new ObjectMapper();
                LoginRequest loginRequest = objectMapper.readValue(receivedJson, LoginRequest.class);

                //Login handling
                String response = handleLogin(loginRequest);

                //Sending response
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println(response);

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String handleLogin(LoginRequest loginRequest) {

        System.out.println("Handling login for: " + loginRequest.getUsername());

        Users user = userDao.getUserByUsername(loginRequest.getUsername());

        if(user != null && user.getPassword().equals(loginRequest.getPassword())) { return "success"; }
        else { return "error"; }
    }
}