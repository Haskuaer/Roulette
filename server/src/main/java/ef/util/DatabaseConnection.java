package ef.util;

import java.sql.*;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/roulette";
    private static final String USER = "root";
    private static final String PASSWORD = "Dsadas123`";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void checkConnection(){
        try(Connection connection = getConnection()) {
            System.out.println("Database connection established successfully");
        } catch (SQLException e) {
            System.out.println("Failed to establish database connection" + e.getMessage());
        }
    }
}
