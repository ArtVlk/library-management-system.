package ru.ArtemVolk.NauJava.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.ArtemVolk.NauJava.crud.repository.BookRepository;
import ru.ArtemVolk.NauJava.crud.repository.ReportRepository;
import ru.ArtemVolk.NauJava.crud.repository.UserRepository;
import ru.ArtemVolk.NauJava.entity.Book;
import ru.ArtemVolk.NauJava.entity.Report;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static ru.ArtemVolk.NauJava.entity.Report.ReportStatus.COMPLETED;
import static ru.ArtemVolk.NauJava.entity.Report.ReportStatus.ERROR;


@Service
@Component
public class ReportServiceImpl implements ReportService{
    @Autowired
    private final ReportRepository reportRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final BookRepository bookRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository,
                             BookRepository bookRepository) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public Report getReport(Long reportId) {
        Optional<Report> report = reportRepository.findById(reportId);
        if (report.isEmpty()) {
            throw new RuntimeException("Report not found");
        }

        return report.orElse(null);
    }

    @Override
    public Long createReport() {
        Report report = new Report();
        report.setStatus(Report.ReportStatus.CREATED);
        reportRepository.save(report);
        return report.getReportId();
    }

    @Async
    public CompletableFuture<Report> generateReport() {
        CompletableFuture<Report> future = CompletableFuture.supplyAsync(() -> {
            Report report = new Report();

            long startTotalTime = System.currentTimeMillis();

            Thread userCountThread = new Thread(() -> {
                long startCountTime = System.currentTimeMillis();
                report.setUserCount(userRepository.count());
                long endCountTime = System.currentTimeMillis();

                report.setUserCountTime(endCountTime - startCountTime);
            });

            Thread booksThread = new Thread(() -> {
                long startBooksTime = System.currentTimeMillis();
                Set<Book> books = new HashSet<>();
                for (Book book : bookRepository.findAll()) {
                    books.add(book);
                }
                report.setBooks(books);
                long endBooksTime = System.currentTimeMillis();
                report.setBooksTime(endBooksTime - startBooksTime);
            });

            userCountThread.start();
            booksThread.start();

            try {
                userCountThread.join();
                booksThread.join();
            } catch (InterruptedException e) {
                report.setStatus(ERROR);
                e.printStackTrace();
            }

            long endTotalTime = System.currentTimeMillis();
            report.setTotalTime(endTotalTime - startTotalTime);

            return report;
        });
        future.thenAccept(report -> {
            report.setStatus(COMPLETED);
            reportRepository.save(report);
            String reportContent = createStringReportContent(report.getUserCount(),
                    report.getUserCountTime(),
                    report.getBooks(),
                    report.getBooksTime(),
                    report.getTotalTime());
            report.setContent(reportContent);

        });
        return future;
    }

    private String createStringReportContent(long userCount, long userCountTime,
                                             Set<Book> books, long booksTime, long totalTime) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Количество зарегистрированных пользователей:").append(userCount).append('\n');
        stringBuilder.append("Затраченное время: ").append(userCountTime).append('\n').append('\n');

        stringBuilder.append("Список книжек: ").append('\n');
        for (Book book : books) stringBuilder.append(" - ").append(book.getName()).append('\n');
        stringBuilder.append("Затраченное время на вывод книжек: ").append(booksTime).append('\n').append('\n');

        stringBuilder.append("Общее затраченное время: ").append(totalTime);

        return stringBuilder.toString();
    }
}
