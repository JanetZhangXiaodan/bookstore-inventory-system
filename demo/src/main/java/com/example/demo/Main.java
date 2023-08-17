package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner demo(Bookstore bookstore) {
        return (args) -> {
            Scanner scanner = new Scanner(System.in);
            DatabaseSetup.setupDatabaseAndLoadData(args);
            boolean programRunning = true;

            while (programRunning) {
                try {
                    System.out.println("Key in Option (Number Only):");
                    System.out.println("1. Add a book to the inventory");
                    System.out.println("2. Remove a book from the inventory");
                    System.out.println("3. Update the quantity in stock for a given book");
                    System.out.println("4. Retrieve the quantity in stock for a given book");
                    System.out.println("5. List all books in the inventory");
                    System.out.println("6. Exit");
                    System.out.print("Select an option: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1:
                            addBook(scanner, bookstore);
                            break;
                        case 2:
                            removeBook(scanner, bookstore);
                            break;
                        case 3:
                            updateQuantity(scanner, bookstore);
                            break;
                        case 4:
                            retrieveQuantity(scanner, bookstore);
                            break;
                        case 5:
                            listAllBooks(scanner, bookstore);
                            break;
                        case 6:
                            System.out.println("Exiting...");
                            programRunning = false;
                            break; // Exit the program
                        default:
                            System.out.println("Invalid option. Please select again.");
                    }
                } catch (Exception e) {
                    logger.error("An error occurred: {}", e.getMessage(), e);
                    System.out.println("An unexpected error occurred. Please try again.");
                }
            }
            scanner.close();
        };
    }
    private static void addBook(Scanner scanner, Bookstore bookstore) {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter author: ");
        String author = scanner.nextLine();

        int isbn = getValidIntInput("Enter ISBN: ");

        double price = getValidDoubleInput("Enter price: ");
        int quantityInStock = getValidIntInput("Enter quantity in stock: ");

        Book newBook = new Book(title, author, isbn, price, quantityInStock);

        // Add the book to the inventory.
        bookstore.addBook(newBook);

        
        System.out.println("Inventory after adding a book:");
        for (Book book : bookstore.getAllBooks()) {
            System.out.println("Title: " + book.getTitle() + ", ISBN: " + book.getIsbn());
        }
        System.out.println("Book added successfully.");
    };

    private static void removeBook(Scanner scanner, Bookstore bookstore) {
        int isbn = getValidIntInput("Enter ISBN of the book to remove: ");

        boolean removed = bookstore.removeBook(isbn);

        if (removed) { 
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Book not found in the inventory.");
        }
    }

    private static void updateQuantity(Scanner scanner, Bookstore bookstore) {
        int isbn = getValidIntInput("Enter ISBN of the book to update: ");
        int newQuantity = getValidIntInput("Enter new quantity: ");
    
        boolean updated = bookstore.updateQuantity(isbn, newQuantity);
    
        if (updated) {
            System.out.println("Quantity updated successfully.");
        } else {
            System.out.println("Book not found in the inventory.");
        }
    }

    private void retrieveQuantity(Scanner scanner, Bookstore bookstore) {
        int isbn = getValidIntInput("Enter ISBN of the book to retrieve: ");
        int quantity = bookstore.getQuantityByIsbn(isbn);

        if (quantity >= 0) {
            System.out.println("Quantity in stock: " + quantity);
        } else {
            System.out.println("Book not found.");
        }
    }

    private void listAllBooks(Scanner scanner, Bookstore bookstore) {
        List<String> allBooks = bookstore.getAllBookNames();

        if (!allBooks.isEmpty()) {
            System.out.println("All books: " + String.join(", ", allBooks));
        } else {
            System.out.println("Books not found.");
        }
    }
 
    private static double getValidDoubleInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        double value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                logger.error("An error occurred: {}", e.getMessage(), e);
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }

    private static int getValidIntInput(String prompt) {
        Scanner scanner = new Scanner(System.in);
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                logger.error("An error occurred: {}", e.getMessage(), e);
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return value;
    }
}
