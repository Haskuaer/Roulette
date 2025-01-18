package ef;

import ef.util.DatabaseConnection;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {

            System.out.println("Server is running on port 12345...");
            DatabaseConnection.checkConnection();

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Handle client communication
                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    String message = in.readLine();
                    System.out.println("Received: " + message);
                    out.println("Server received: " + message);


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}