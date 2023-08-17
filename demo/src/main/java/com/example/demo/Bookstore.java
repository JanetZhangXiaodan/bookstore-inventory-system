package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class Bookstore {
    private BookRepository bookRepository;
    private Map<Integer, Book> inventory;

    public Bookstore(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.inventory = new HashMap<>();
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(inventory.values());
    }

    public void addBook(Book book) {
        inventory.put(book.getIsbn(), book);
        bookRepository.insertBook(book); // Insert into database
    }

    public boolean removeBook(int isbn) {
        boolean removedFromDatabase = bookRepository.removeBook(isbn);
    
        if (removedFromDatabase) {
            inventory.remove(isbn); // Remove from the in-memory inventory
            return true; // Successfully removed from both database and inventory
        } else if (inventory.containsKey(isbn)) {
            inventory.remove(isbn); // Remove from the in-memory inventory
            return true; // Removed from inventory (secondary) as book exists in memory
        }
    
        return false; // Book not found in both database and inventory
    }
    
    public boolean updateQuantity(int isbn, int newQuantity) {
        boolean updatedInDatabase = bookRepository.updateQuantity(isbn, newQuantity);
    
        if (updatedInDatabase) {
            if (inventory.containsKey(isbn)) {
                Book book = inventory.get(isbn);
                book.setQuantityInStock(newQuantity);
            }
            return true; // Successfully updated in database
        }
        return false; // Failed to update in database
    }   

    public int getQuantityByIsbn(int isbn) {
        Integer quantity = bookRepository.findQuantityByIsbn(isbn);
        if (quantity != null) {
            return quantity;
        }
        return -1; // Return a sentinel value if the book is not found
    }

    public List<String> getAllBookNames() {
        return bookRepository.findAllBookNames();
    }

}
 