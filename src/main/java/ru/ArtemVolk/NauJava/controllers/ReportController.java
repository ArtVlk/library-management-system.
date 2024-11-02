package ru.ArtemVolk.NauJava.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.ArtemVolk.NauJava.entity.Report;
import ru.ArtemVolk.NauJava.services.ReportServiceImpl;

import java.util.concurrent.CompletableFuture;

@Controller
public class ReportController {
    private final ReportServiceImpl reportService;

    @Autowired
    public ReportController(ReportServiceImpl reportService) {
        this.reportService = reportService;
    }


    @GetMapping("/report")
    public String getReport(Model model) {
        CompletableFuture<Report> future = reportService.generateReport();
        try {
            Report reportFromFuture = future.get();
            model.addAttribute(reportFromFuture);
            return "report";

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "exception";
    }

    @GetMapping("/report/{id}")
    public String getReportById(@PathVariable Long id, Model model) {
        Report report = reportService.getReport(id);

        if (report != null) {
            if (report.getStatus() == Report.ReportStatus.COMPLETED) {
                model.addAttribute("report", report);
                return "report";
            } else if (report.getStatus() == Report.ReportStatus.CREATED) {
                return "report_not_ready";
            } else {
                return "report_error";
            }
        } else {
            return "report_not_found";
        }
    }
}
