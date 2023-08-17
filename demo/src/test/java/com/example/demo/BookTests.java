package com.example.demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BookTests {

    @Test
    public void testBookConstructorAndGetters() {
        String title = "Sample Book";
        String author = "John Doe";
        int isbn = 123456789;
        double price = 19.99;
        int quantityInStock = 10;

        Book book = new Book(title, author, isbn, price, quantityInStock);

        assertEquals(title, book.getTitle());
        assertEquals(author, book.getAuthor());
        assertEquals(isbn, book.getIsbn());
        assertEquals(price, book.getPrice(), 0.001); // Allow for a small tolerance in double comparison
        assertEquals(quantityInStock, book.getQuantityInStock());
    }

    @Test
    public void testBookSetters() {
        Book book = new Book();

        String newTitle = "New Book Title";
        String newAuthor = "Jane Smith";
        int newIsbn = 987654321;
        double newPrice = 24.99;
        int newQuantityInStock = 5;

        book.setTitle(newTitle);
        book.setAuthor(newAuthor);
        book.setIsbn(newIsbn);
        book.setPrice(newPrice);
        book.setQuantityInStock(newQuantityInStock);

        assertEquals(newTitle, book.getTitle());
        assertEquals(newAuthor, book.getAuthor());
        assertEquals(newIsbn, book.getIsbn());
        assertEquals(newPrice, book.getPrice(), 0.001);
        assertEquals(newQuantityInStock, book.getQuantityInStock());
    }
}
