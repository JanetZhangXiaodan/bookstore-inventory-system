package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BookstoreTests {

    @MockBean
    private BookRepository mockBookRepository;

    @Test
    public void testRemoveBookFromDatabase() {
        int isbn = 123456;
        when(mockBookRepository.removeBook(isbn)).thenReturn(true);

        Bookstore bookstore = new Bookstore(mockBookRepository);
        boolean removed = bookstore.removeBook(isbn);

        assertTrue(removed);

        verify(mockBookRepository, times(1)).removeBook(isbn);
    }

    @Test
    public void testRemoveBookFromMemory() {
        int isbn = 123456;
        when(mockBookRepository.removeBook(isbn)).thenReturn(false);

        Bookstore bookstore = new Bookstore(mockBookRepository);
        boolean removed = bookstore.removeBook(isbn);

        assertTrue(removed);

        verify(mockBookRepository, times(1)).removeBook(isbn);
    }

    @Test
    public void testRemoveBookNotFound() {
        int isbn = 123456;
        when(mockBookRepository.removeBook(isbn)).thenReturn(false);

        Bookstore bookstore = new Bookstore(mockBookRepository);
        boolean removed = bookstore.removeBook(isbn);

        assertFalse(removed);

        verify(mockBookRepository, times(1)).removeBook(isbn);
    }

    @Test
    public void testUpdateQuantitySuccess() {
        int isbn = 123456;
        int newQuantity = 10;
        when(mockBookRepository.updateQuantity(isbn, newQuantity)).thenReturn(true);

        Bookstore bookstore = new Bookstore(mockBookRepository);
        boolean updated = bookstore.updateQuantity(isbn, newQuantity);

        assertTrue(updated);

        verify(mockBookRepository, times(1)).updateQuantity(isbn, newQuantity);
    }

    @Test
    public void testUpdateQuantityFailure() {
        int isbn = 123456;
        int newQuantity = 10;
        when(mockBookRepository.updateQuantity(isbn, newQuantity)).thenReturn(false);

        Bookstore bookstore = new Bookstore(mockBookRepository);
        boolean updated = bookstore.updateQuantity(isbn, newQuantity);

        assertFalse(updated);

        verify(mockBookRepository, times(1)).updateQuantity(isbn, newQuantity);
    }

}
