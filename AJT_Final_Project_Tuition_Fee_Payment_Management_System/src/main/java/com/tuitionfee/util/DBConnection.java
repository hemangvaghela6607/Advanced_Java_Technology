package com.tuitionfee.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Reusable JDBC Connection Utility for MySQL (XAMPP/phpMyAdmin).
 * Demonstrates standard JDBC DriverManager connection handling,
 * reading configuration from db.properties with safe fallback defaults.
 */
public class DBConnection {

    private static String url = "jdbc:mysql://localhost:3306/tuition_fee_management?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static String username = "root";
    private static String password = "";
    private static String driver = "com.mysql.cj.jdbc.Driver";

    static {
        try {
            // Load configuration from db.properties if available
            InputStream in = DBConnection.class.getClassLoader().getResourceAsStream("db.properties");
            if (in != null) {
                Properties props = new Properties();
                props.load(in);
                url = props.getProperty("db.url", url);
                username = props.getProperty("db.username", username);
                password = props.getProperty("db.password", password);
                driver = props.getProperty("db.driver", driver);
            }
            // Register MySQL JDBC Driver class
            Class.forName(driver);
        } catch (Exception e) {
            System.err.println("[DBConnection] Error loading database driver or properties: " + e.getMessage());
        }
    }

    /**
     * Obtains a new JDBC Connection to MySQL.
     * @return Connection object
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Safely closes a JDBC connection.
     * @param connection the Connection to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("[DBConnection] Failed to close connection: " + e.getMessage());
            }
        }
    }
}
