package vn.edu.iuh.fit.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.models.PaymentStatus;
import vn.edu.iuh.fit.models.Quote;
import vn.edu.iuh.fit.models.dto.FinancialReportDto;

import vn.edu.iuh.fit.models.dto.OperationalReportDto;
import vn.edu.iuh.fit.models.dto.ResaleItemDto;
import vn.edu.iuh.fit.models.dto.ResaleRevenueReportDto;
import vn.edu.iuh.fit.repository.QuoteRepository;
import vn.edu.iuh.fit.repository.RecyclingRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final QuoteRepository quoteRepository;
    private final RecyclingRepository recyclingReceiptRepository;
    private static final double SHIPPING_COST_PER_ITEM = 50000; // Chi phí vận chuyển mỗi sản phẩm
    private static final double PROCESSING_COST_PER_ITEM = 20000; // Chi phí xử lý mỗi sản phẩm

    public FinancialReportDto generateFinancialReport(LocalDate startDate, LocalDate endDate) {
        Double totalRevenue = recyclingReceiptRepository.sumFinalPriceByPaymentStatusAndCreatedAtBetween(
                PaymentStatus.PAID, startDate, endDate);
        totalRevenue = totalRevenue != null ? totalRevenue : 0.0;
        int totalPaidItems = recyclingReceiptRepository.countByPaymentStatusAndCreatedAtBetween(
                PaymentStatus.PAID, startDate, endDate);
        double totalExpenses = totalPaidItems * (SHIPPING_COST_PER_ITEM + PROCESSING_COST_PER_ITEM);
        //  lợi nhuận ròng
        double netProfit = totalRevenue - totalExpenses;

        return new FinancialReportDto(totalRevenue, totalExpenses, netProfit);
    }

    // bao cao hoat dong khi tai che Completed
//    public OperationalReportDto generateOperationalReport(LocalDate startDate, LocalDate endDate) {
//        OperationalReportDto report = new OperationalReportDto();
//        List<Object[]> operationalData = recyclingReceiptRepository.getOperationalReportData(startDate, endDate);
//
//        for (Object[] row : operationalData) {
//            report.setTotalRecycledDevices(report.getTotalRecycledDevices() + (row[0] != null ? ((Number) row[0]).intValue() : 0));
//            report.getDevicesByType().put((String) row[1], (row[0] != null ? ((Number) row[0]).intValue() : 0));
//            report.setAverageRecyclingTime(report.getAverageRecyclingTime() + (row[3] != null ? ((Number) row[3]).doubleValue() : 0));
//        }
//
//        if (!operationalData.isEmpty()) {
//            report.setAverageRecyclingTime(report.getAverageRecyclingTime() / operationalData.size()); // Tính trung bình
//        }
//
//        // Tính toán các chỉ số khác (tỷ lệ thành công, số lượng chờ xử lý,...)
//        // ... ví dụ:
////        long completedCount = recyclingReceiptRepository.countByRecyclingReceiptStatusAndCreatedAtBetween(RecyclingReceiptStatus.COMPLETED, startDate, endDate);
////        long pendingCount = recyclingReceiptRepository.countByRecyclingReceiptStatusAndCreatedAtBetween(RecyclingReceiptStatus.PENDING, startDate, endDate);
////        report.setSuccessRate(completedCount * 100.0 / report.getTotalRecycledDevices());
////        report.setPendingDevices(pendingCount);
//
//        return report;
//    }
    public ResaleRevenueReportDto generateResaleRevenueReport(LocalDate startDate, LocalDate endDate) {
        ResaleRevenueReportDto report = new ResaleRevenueReportDto();
        List<Object[]> data = quoteRepository.getResaleItemData(startDate, endDate);

        for (Object[] row : data) {
            ResaleItemDto itemDto = new ResaleItemDto();
            itemDto.setQuoteCode((String) row[0]);
            itemDto.setTotalRevenue(((Number) row[1]).doubleValue());
            itemDto.setQuantitySold(((Number) row[2]).intValue());
            // Lấy thông tin sản phẩm (tên, loại, thương hiệu,...) từ productRepository
            Quote quote = quoteRepository.findByProductCode(itemDto.getQuoteCode());
            if (quote != null) {
                itemDto.setModel(quote.getDevice().getModel());
                itemDto.setDeviceType(quote.getDevice().getDeviceType()); // Giả sử có category trong Product
            }
            report.getResaleItem().add(itemDto);
            report.setTotalResaleRevenue(report.getTotalResaleRevenue() + itemDto.getTotalRevenue());
        }
        return report;
    }
}
