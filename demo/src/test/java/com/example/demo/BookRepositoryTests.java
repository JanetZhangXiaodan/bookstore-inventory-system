package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@SpringBootTest
public class BookRepositoryTests {

    @MockBean
    private DataSource mockDataSource;

    @MockBean
    private Connection mockConnection;

    @MockBean
    private PreparedStatement mockPreparedStatement;

    @MockBean
    private ResultSet mockResultSet;

    @Test
    public void testInsertBook() throws Exception {
        BookRepository bookRepository = new BookRepository(mockDataSource);
        Book book = new Book("Test Book", "Test Author", 123456, 29.99, 10);

        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        
        // Mock executeUpdate to return a value
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        bookRepository.insertBook(book);

        verify(mockPreparedStatement).setString(1, book.getTitle());
        verify(mockPreparedStatement).setString(2, book.getAuthor());
        verify(mockPreparedStatement).setInt(3, book.getIsbn());
        verify(mockPreparedStatement).setDouble(4, book.getPrice());
        verify(mockPreparedStatement).setInt(5, book.getQuantityInStock());
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testRemoveBook() throws Exception {
        BookRepository bookRepository = new BookRepository(mockDataSource);
        int isbn = 123456;

        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        
        // Mock executeUpdate to return a value
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = bookRepository.removeBook(isbn);

        assertTrue(result);
        verify(mockPreparedStatement).setInt(1, isbn);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testFindQuantityByIsbn() throws Exception {
        BookRepository bookRepository = new BookRepository(mockDataSource);
        int isbn = 123456;

        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Mock resultSet to return a value
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("quantityInStock")).thenReturn(10);

        Integer quantity = bookRepository.findQuantityByIsbn(isbn);

        assertNotNull(quantity);
        assertEquals(10, quantity);
        verify(mockPreparedStatement).setInt(1, isbn);
        verify(mockResultSet).getInt("quantityInStock");
    }

    @Test
    public void testUpdateQuantity() throws Exception {
        BookRepository bookRepository = new BookRepository(mockDataSource);
        int isbn = 123456;
        int newQuantity = 15;

        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        
        // Mock executeUpdate to return a value
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        boolean result = bookRepository.updateQuantity(isbn, newQuantity);

        assertTrue(result);
        verify(mockPreparedStatement).setInt(1, newQuantity);
        verify(mockPreparedStatement).setInt(2, isbn);
        verify(mockPreparedStatement).executeUpdate();
    }

    @Test
    public void testFindAllBookNames() throws Exception {
        BookRepository bookRepository = new BookRepository(mockDataSource);

        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Mock resultSet to return multiple values
        when(mockResultSet.next()).thenReturn(true, true, false);
        when(mockResultSet.getString("title")).thenReturn("Book 1", "Book 2");

        List<String> bookNames = bookRepository.findAllBookNames();

        assertNotNull(bookNames);
        assertEquals(2, bookNames.size());
        assertEquals("Book 1", bookNames.get(0));
        assertEquals("Book 2", bookNames.get(1));
        verify(mockResultSet, times(2)).getString("title");
    }

    // Other test methods
}

