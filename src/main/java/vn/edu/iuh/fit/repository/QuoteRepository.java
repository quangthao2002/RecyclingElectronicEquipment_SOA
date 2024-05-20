package vn.edu.iuh.fit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.fit.models.Quote;
import vn.edu.iuh.fit.models.QuoteStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    @Query("SELECT q FROM Quote q WHERE q.quoteStatus = ?1")
    List<Quote> getQuotesByQuoteStatus(QuoteStatus quoteStatus);

    @Query("SELECT q FROM Quote q JOIN q.recyclingReceipt r WHERE r.user.id = :userId")
    List<Quote> findByRecyclingReceiptUserId(@Param("userId") Long userId);

    @Query("SELECT SUM(q.finalQuotePrice) FROM Quote q WHERE q.createdAt BETWEEN :startDate AND :endDate")
    double getTotalRevenue(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT q.productCode, SUM(q.finalQuotePrice), COUNT(q) FROM Quote q WHERE q.createdAt BETWEEN :startDate AND :endDate GROUP BY q.productCode")
    List<Object[]> getResaleItemData(LocalDateTime startDate, LocalDateTime endDate);
}