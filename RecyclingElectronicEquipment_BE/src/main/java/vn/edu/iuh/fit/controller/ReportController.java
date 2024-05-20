package vn.edu.iuh.fit.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.iuh.fit.models.dto.FinancialReportDto;
import vn.edu.iuh.fit.models.dto.OperationalReportDto;
import vn.edu.iuh.fit.models.dto.ResaleRevenueReportDto;
import vn.edu.iuh.fit.response.ErrorResponseDto;
import vn.edu.iuh.fit.services.Impl.ReportService;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;
    private static final Logger log = LoggerFactory.getLogger(RecyclingReceiptController.class);

    // bao cao tai chinh
    @GetMapping("/reports/financial")
    public ResponseEntity<FinancialReportDto> getFinancialReport(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        try {
            FinancialReportDto report = reportService.generateFinancialReport(startDate, endDate);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            log.error("Error generating financial report", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @GetMapping("/operational")
//    public ResponseEntity<OperationalReportDto> getOperationalReport(
//            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
//            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
//        try {
//            OperationalReportDto report = reportService.generateOperationalReport(startDate, endDate);
//            return ResponseEntity.ok(report);
//        } catch (Exception e) {
//            log.error("Error generating operational report", e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @GetMapping("/resale-revenue")
    public ResponseEntity<ResaleRevenueReportDto> getResaleRevenueReport(
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate){
        try {
            ResaleRevenueReportDto report = reportService.generateResaleRevenueReport(startDate, endDate);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            log.error("Error generating resale revenue report", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
