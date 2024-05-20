package vn.edu.iuh.fit.services;

import vn.edu.iuh.fit.models.Quote;
import vn.edu.iuh.fit.models.QuoteStatus;

import java.util.List;

public interface IQuoteService {
    Quote saveQuote(Quote quote );
    Quote getQuoteById(Long id);
    List<Quote>  getQuotesByQuoteStatus(QuoteStatus status);
    List<Quote> getAllQuotes();
    List<Quote> findByRecyclingReceiptUserId(Long userId);
}
