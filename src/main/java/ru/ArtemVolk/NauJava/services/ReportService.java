package ru.ArtemVolk.NauJava.services;

import ru.ArtemVolk.NauJava.entity.Report;

import java.util.concurrent.CompletableFuture;

public interface ReportService {
    Report getReport(Long reportId);
    Long createReport();
    CompletableFuture<Report> generateReport();
}
