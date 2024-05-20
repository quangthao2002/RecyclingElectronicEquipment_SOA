package vn.edu.iuh.fit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.fit.models.Quote;
import vn.edu.iuh.fit.models.QuoteStatus;

import java.time.LocalDate;
import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    Quote findByProductCode(String productCode);
    @Query("SELECT q FROM Quote q WHERE q.quoteStatus = ?1")
    List<Quote> getQuotesByQuoteStatus(QuoteStatus quoteStatus);

    @Query("SELECT q FROM Quote q JOIN q.recyclingReceipt r WHERE r.user.id = :userId")
    List<Quote> findByRecyclingReceiptUserId(@Param("userId") Long userId);


    @Query("SELECT q.productCode, SUM(q.finalQuotePrice), COUNT(q), d.model, d.deviceAge, d.deviceStatus " +
            "FROM Quote q " +
            "JOIN q.device d " +
            "WHERE q.quoteStatus = 'PAID' " +
            "AND q.createdAt BETWEEN :startDate AND :endDate " +
            "GROUP BY q.productCode, d.deviceType, d.model, d.deviceAge, d.deviceStatus")
    List<Object[]> getResaleItemData(LocalDate startDate, LocalDate endDate);

}