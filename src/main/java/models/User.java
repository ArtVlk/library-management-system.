package models;

import java.util.List;
import java.util.ArrayList;

public class User {
    private Long id;
    private String name;
    private String email;
    private List<Book> borrowedBooks;

    public User() {
        borrowedBooks = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getBorrowedBooksList() {
        List<String> bookTitles = new ArrayList<>();
        for (Book book : borrowedBooks) {
            bookTitles.add(book.getTitle());
        }
        return bookTitles;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }
}
