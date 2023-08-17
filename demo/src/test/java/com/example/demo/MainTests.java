package com.example.demo;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.util.Collections;

import static org.mockito.Mockito.*;

@SpringBootTest
public class MainTests {

    @Mock
    private Bookstore mockBookstore;

    @Test
    public void testAddBook() {
        String input = "Sample Title\nSample Author\n123456\n10.0\n100\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        verify(mockBookstore, times(1)).addBook(any(Book.class));
    }

    @Test
    public void testRemoveBook() {
        String input = "123456\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(mockBookstore.removeBook(123456)).thenReturn(true);

        Main.main(new String[]{});

        verify(mockBookstore, times(1)).removeBook(123456);
    }

    @Test
    public void testUpdateQuantity() {
        String input = "123456\n50\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(mockBookstore.updateQuantity(123456, 50)).thenReturn(true);

        Main.main(new String[]{});

        verify(mockBookstore, times(1)).updateQuantity(123456, 50);
    }

    @Test
    public void testRetrieveQuantity() {
        String input = "123456\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(mockBookstore.getQuantityByIsbn(123456)).thenReturn(100);

        Main.main(new String[]{});

        verify(mockBookstore, times(1)).getQuantityByIsbn(123456);
    }

    @Test
    public void testListAllBooks() {
        when(mockBookstore.getAllBookNames()).thenReturn(Collections.singletonList("Sample Title"));

        Main.main(new String[]{});

        verify(mockBookstore, times(1)).getAllBookNames();
    }
}

