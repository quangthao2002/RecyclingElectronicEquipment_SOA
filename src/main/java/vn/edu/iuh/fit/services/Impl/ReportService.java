package vn.edu.iuh.fit.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.models.PaymentStatus;
import vn.edu.iuh.fit.models.dto.FinancialReportDto;

import vn.edu.iuh.fit.repository.QuoteRepository;
import vn.edu.iuh.fit.repository.RecyclingRepository;

import java.time.LocalDate;

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

    //  bao cao doanh thu ban lai
//    public ResaleRevenueReportDto generateResaleRevenueReport(LocalDateTime startDate, LocalDateTime endDate) {
//
//        ResaleRevenueReportDto report = new ResaleRevenueReportDto();
//        report.setTotalResaleRevenue(quoteRepository.getTotalRevenue(startDate, endDate));
//        List<ResaleItemDto> resaleItems = new ArrayList<>();
//        List<Object[]> result = quoteRepository.getResaleItemData(startDate, endDate);
//        for (Object[] row : result) {
//            ResaleItemDto itemDto = new ResaleItemDto();
//            itemDto.setProductName((String) row[0]);
//            itemDto.setQuantitySold(((Number) row[1]).intValue());
//            itemDto.setUnitPrice(((Number) row[2]).doubleValue());
//            resaleItems.add(itemDto);
//        }
//
//        report.setResaleItem(resaleItems);
//        return report;
//    }
//
//    // bao cao hoat dong
//    public OperationalReportDto generateOperationalReport(LocalDateTime startDate, LocalDateTime endDate) {
//        OperationalReportDto operationalReportDto = new OperationalReportDto();
//        return operationalReportDto;
//    }
}
