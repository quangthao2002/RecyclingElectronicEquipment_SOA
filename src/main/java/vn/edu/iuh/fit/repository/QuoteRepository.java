package vn.edu.iuh.fit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vn.edu.iuh.fit.models.Quote;
import vn.edu.iuh.fit.models.QuoteStatus;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    @Query("SELECT q FROM Quote q WHERE q.quoteStatus = ?1")
    List<Quote> getQuotesByQuoteStatus(QuoteStatus quoteStatus);

    @Query("SELECT q FROM Quote q JOIN q.recyclingReceipt r WHERE r.user.id = :userId")
    List<Quote> findByRecyclingReceiptUserId(@Param("userId") Long userId);

}