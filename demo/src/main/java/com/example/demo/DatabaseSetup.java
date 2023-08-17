package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseSetup {
    public static void setupDatabaseAndLoadData(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/bookstore_db";
        String dbName = "bookstore_db";
        String tableName = "books";
        String username = "root";
        String password = "janetroot";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            createDatabase(connection, dbName);
            createTable(connection, tableName);
            loadDataFromFile(connection, "C:/ProgramData/MySQL/MySQL Server 8.1/Uploads/book.txt", tableName);
            System.out.println("Database setup completed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createDatabase(Connection connection, String dbName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createDbQuery = "CREATE DATABASE IF NOT EXISTS " + dbName;
            statement.executeUpdate(createDbQuery);
        }
        System.out.println("Database has been created.");
    }

    private static void createTable(Connection connection, String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "title VARCHAR(255)," +
                    "author VARCHAR(255)," +
                    "isbn VARCHAR(20) UNIQUE," +
                    "price FLOAT(7,4)," +
                    "quantityInStock INT);";
            statement.executeUpdate(createTableQuery);
        }
        System.out.println("Table has been created.");
    }

    private static void loadDataFromFile(Connection connection, String filePath, String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            String loadDataQuery = "LOAD DATA INFILE '" + filePath + "' INTO TABLE " + tableName +
                    " FIELDS TERMINATED BY ','";
            statement.executeUpdate(loadDataQuery);
        }
        System.out.println("Initial book stock has been uploaded.");
    }
}

