package ru.ArtemVolk.NauJava.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(255)")
    private ReportStatus status;
    @Column(columnDefinition = "TEXT")
    private String content;
    private Long userCount;
    private Long userCountTime;
    private Long booksTime;
    private Long totalTime;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "reports_and_books",
            joinColumns = @JoinColumn(name = "report_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> books = new HashSet<>();

    public enum ReportStatus {
        CREATED,
        COMPLETED,
        ERROR
    }

    public ReportStatus getStatus() {
        return status;
    }

    public void setStatus(ReportStatus status) {
        this.status = status;
    }


    public Long getReportId() {
        return id;
    }

    public void setReportId(Long reportId) {
        this.id = reportId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public Long getUserCountTime() {
        return userCountTime;
    }

    public void setUserCountTime(Long userCountTime) {
        this.userCountTime = userCountTime;
    }

    public Long getBooksTime() {
        return booksTime;
    }

    public void setBooksTime(Long booksTime) {
        this.booksTime = booksTime;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
}
