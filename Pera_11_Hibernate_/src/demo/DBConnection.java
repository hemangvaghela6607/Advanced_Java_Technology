package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Change these values according to your MySQL setup.
    private static final String URL =
            "jdbc:mysql://localhost:3306/college_db";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {

        // JDBC establishes the connection with MySQL.
        return DriverManager.getConnection(
                URL, USERNAME, PASSWORD);
    }
}
