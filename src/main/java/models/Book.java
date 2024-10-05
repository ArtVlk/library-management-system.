package models;

public class Book {
    private Long id;
    private String title;
    private String author;
    private boolean available;

    public Book(long l, String s, String s1, boolean b) {
        this.id = l;
        this.title = s;
        this.author = s1;
        this.available = b;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
