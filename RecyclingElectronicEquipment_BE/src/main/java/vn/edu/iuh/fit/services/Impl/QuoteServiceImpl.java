package vn.edu.iuh.fit.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.models.Quote;
import vn.edu.iuh.fit.models.QuoteStatus;
import vn.edu.iuh.fit.repository.QuoteRepository;
import vn.edu.iuh.fit.services.IQuoteService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuoteServiceImpl implements IQuoteService {

    private final QuoteRepository quoteRepository;
    @Override
    public Quote saveQuote(Quote quote) {
        return quoteRepository.save(quote);
    }

    @Override
    public Quote getQuoteById(Long id) {
        return quoteRepository.findById(id).orElse(null);
    }

    @Override
    public List<Quote> getQuotesByQuoteStatus(QuoteStatus status) {
        return quoteRepository.getQuotesByQuoteStatus(status);
    }

    @Override
    public List<Quote> getAllQuotes() {
        return quoteRepository.findAll();
    }

    @Override
    public List<Quote> findByRecyclingReceiptUserId(Long userId) {
        return quoteRepository.findByRecyclingReceiptUserId(userId);
    }
}
