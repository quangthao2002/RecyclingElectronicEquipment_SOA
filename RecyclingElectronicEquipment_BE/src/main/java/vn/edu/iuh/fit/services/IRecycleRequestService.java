package vn.edu.iuh.fit.services;

import vn.edu.iuh.fit.models.RecyclingReceipt;
import vn.edu.iuh.fit.models.RecyclingReceiptStatus;
import vn.edu.iuh.fit.models.dto.DeviceRequestDto;

import java.util.List;

public interface IRecycleRequestService {
        double calculateEstimatedPrice(DeviceRequestDto quoteRequestDto);
        void saveQuoteRequest(RecyclingReceipt recycleRequest);
        List<RecyclingReceipt> getRecyclingReceiptsByStatus(RecyclingReceiptStatus status);
        List<RecyclingReceipt> getAllRecyclingReceipts();
         RecyclingReceipt saveRecyclingReceipt(RecyclingReceipt recycleRequest);
        RecyclingReceipt getRecycleRequestById(Long id);
        RecyclingReceipt updateRecycleRequest(RecyclingReceipt recycleRequest);
}
