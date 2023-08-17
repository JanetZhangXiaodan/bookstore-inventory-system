package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

public class BookstoreTests {

    @Mock
    private BookRepository bookRepository;

    private Bookstore bookstore;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        bookstore = new Bookstore(bookRepository);
    }

    @Test
    public void testAddBook() {
        Book book = new Book("Sample Book", "John Doe", 123456789, 19.99, 10);
        when(bookRepository.insertBook(book)).thenReturn(true);

        bookstore.addBook(book);

        List<Book> allBooks = bookstore.getAllBooks();
        assertEquals(1, allBooks.size());
        assertEquals(book, allBooks.get(0));
    }

    @Test
    public void testRemoveBook() {
        int isbn = 123456789;
        when(bookRepository.removeBook(isbn)).thenReturn(true);

        assertTrue(bookstore.removeBook(isbn));
        assertFalse(bookstore.removeBook(987654321)); // Book not found

        List<Book> allBooks = bookstore.getAllBooks();
        assertEquals(0, allBooks.size());
    }

    @Test
    public void testUpdateQuantity() {
        int isbn = 123456789;
        int newQuantity = 15;
        when(bookRepository.updateQuantity(isbn, newQuantity)).thenReturn(true);

        assertTrue(bookstore.updateQuantity(isbn, newQuantity));
        assertFalse(bookstore.updateQuantity(987654321, newQuantity)); // Book not found

        List<Book> allBooks = bookstore.getAllBooks();
        assertEquals(newQuantity, allBooks.get(0).getQuantityInStock());
    }

    @Test
    public void testGetQuantityByIsbn() {
        int isbn = 123456789;
        when(bookRepository.findQuantityByIsbn(isbn)).thenReturn(10);

        int quantity = bookstore.getQuantityByIsbn(isbn);

        assertEquals(10, quantity);
        assertEquals(-1, bookstore.getQuantityByIsbn(987654321)); // Book not found
    }

    @Test
    public void testGetAllBookNames() {
        List<String> bookNames = new ArrayList<>();
        bookNames.add("Book 1");
        bookNames.add("Book 2");
        when(bookRepository.findAllBookNames()).thenReturn(bookNames);

        List<String> allBookNames = bookstore.getAllBookNames();

        assertEquals(bookNames, allBookNames);
    }
}
