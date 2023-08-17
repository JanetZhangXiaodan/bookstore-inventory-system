package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Repository
public class BookRepository {
    private static final Logger logger = LoggerFactory.getLogger(BookRepository.class);
    private final DataSource dataSource;

    public BookRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void insertBook(Book book) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                 "INSERT INTO books (title, author, isbn, price, quantityInStock) VALUES (?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getIsbn());
            preparedStatement.setDouble(4, book.getPrice());
            preparedStatement.setInt(5, book.getQuantityInStock());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
            e.printStackTrace();
        }
    }
    
    public boolean removeBook(int isbn) {
        String query = "DELETE FROM books WHERE isbn = ?";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, isbn);
            statement.executeUpdate(); // Use executeUpdate to execute DELETE operation
        } catch (SQLException e) {
            handleSQLException(e);
            e.printStackTrace();
        }
        return true;
    }

    public Integer findQuantityByIsbn(int isbn) {
        String query = "SELECT b.quantityInStock FROM books b WHERE b.isbn = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, isbn);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("quantityInStock");
                }
            }
        } catch (SQLException e) {
            handleSQLException(e);
            e.printStackTrace();
        }
        return null; // Return null if the ISBN is not found
    }

    public boolean updateQuantity(int isbn, int newQuantity) {
        String query = "UPDATE books SET quantityInStock = ? WHERE isbn = ?";
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, newQuantity);
            statement.setInt(2, isbn);
            statement.executeUpdate();
        } catch (SQLException e) {
            handleSQLException(e);
            e.printStackTrace();
        }
        return true;
    }

    public List<String> findAllBookNames() {
        List<String> bookNames = new ArrayList<>();
        String query = "SELECT DISTINCT title FROM books";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                bookNames.add(resultSet.getString("title"));
            }
        } catch (SQLException e) {
            handleSQLException(e);
            e.printStackTrace();
        }
        return bookNames;
    }


    private void handleSQLException(SQLException e) {
        // Log the exception and possibly take other actions like throwing a custom exception
        logger.error("An SQL exception occurred: {}" , e.getMessage(), e);
        e.printStackTrace();
    }
}
