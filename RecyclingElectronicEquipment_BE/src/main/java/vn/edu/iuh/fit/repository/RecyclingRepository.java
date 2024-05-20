package vn.edu.iuh.fit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.edu.iuh.fit.models.PaymentStatus;
import vn.edu.iuh.fit.models.RecyclingReceipt;
import vn.edu.iuh.fit.models.RecyclingReceiptStatus;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RecyclingRepository extends JpaRepository<RecyclingReceipt, Long> {

    @Query("SELECT r FROM RecyclingReceipt r WHERE r.recyclingReceiptStatus = ?1")
    List<RecyclingReceipt> getRecyclingReceiptsByRecyclingReceiptStatus(RecyclingReceiptStatus status);

    @Query("SELECT SUM(r.quote.finalQuotePrice) FROM RecyclingReceipt r WHERE r.paymentStatus = :paymentStatus AND r.createdAt BETWEEN :startDate AND :endDate")
    Double sumFinalPriceByPaymentStatusAndCreatedAtBetween(@Param("paymentStatus") PaymentStatus paymentStatus, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(r) FROM RecyclingReceipt r WHERE r.paymentStatus = :paymentStatus AND r.createdAt BETWEEN :startDate AND :endDate")
    int countByPaymentStatusAndCreatedAtBetween(@Param("paymentStatus") PaymentStatus paymentStatus, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

//    @Query("SELECT COUNT(r), r.quote.device.deviceType, AVG(r.updatedAt - r.createdAt) " +
//            "FROM RecyclingReceipt r " +
//            "WHERE r.recyclingReceiptStatus = 'COMPLETED' " +
//            "AND r.createdAt BETWEEN :startDate AND :endDate " +
//            "GROUP BY r.quote.device.deviceType")
//    List<Object[]> getOperationalReportData(LocalDate startDate, LocalDate endDate);
}
