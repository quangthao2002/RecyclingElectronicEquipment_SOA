package vn.edu.iuh.fit.services;

import vn.edu.iuh.fit.models.RecycleRequest;
import vn.edu.iuh.fit.models.dto.QuoteRequestDto;

import java.util.Optional;

public interface IRecycleRequestService {
        double calculateEstimatedPrice(QuoteRequestDto quoteRequestDto);
        void saveQuoteRequest(RecycleRequest recycleRequest);
         RecycleRequest saveRecycleRequest(RecycleRequest recycleRequest);
        Optional<RecycleRequest> getRecycleRequestById(Long id);
        RecycleRequest updateRecycleRequest(RecycleRequest recycleRequest);
}
