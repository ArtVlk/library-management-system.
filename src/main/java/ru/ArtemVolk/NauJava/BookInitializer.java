package ru.ArtemVolk.NauJava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import models.Book;
import java.util.List;

@Component
public class BookInitializer {
    private final List<Book> bookContainer;

    @Autowired
    public BookInitializer(List<Book> bookContainer) {
        this.bookContainer = bookContainer;
    }

    public void initializeBooks() {
        bookContainer.add(new Book(1, "Book 1", "Author 1", true));
        bookContainer.add(new Book(2, "Book 2", "Author 2", true));
        bookContainer.add(new Book(3, "Book 3", "Author 3", true));
    }
}