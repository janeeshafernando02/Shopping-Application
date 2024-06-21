package org.example;

//Import Statements
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {
    //Initializing instance variables
    private static final String DB_URL = "jdbc:mysql://localhost:3306/westminster_shopping_center_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Moratuwa123@";

    // Establish a database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // Close the database connection
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
