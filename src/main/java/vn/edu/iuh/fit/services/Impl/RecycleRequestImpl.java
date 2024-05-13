package vn.edu.iuh.fit.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.models.RecycleRequest;
import vn.edu.iuh.fit.models.dto.QuoteRequestDto;
import vn.edu.iuh.fit.repository.RecycleRepository;

import vn.edu.iuh.fit.services.IRecycleRequestService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecycleRequestImpl implements IRecycleRequestService {

    private final RecycleRepository recycleRepository;

    @Override
    public double calculateEstimatedPrice(QuoteRequestDto quoteRequestDto) {
        double initialPrice = quoteRequestDto.getInitialPrice();
        int deviceAge = quoteRequestDto.getDeviceAge();
        String status = quoteRequestDto.getStatus();
        String damageLocation = quoteRequestDto.getDamageLocation();

        double discount = 0;

        // Áp dụng giảm giá dựa trên tuổi đời của thiết bị
        if (deviceAge > 1 && deviceAge <= 3) {
            discount = 0.1;
        } else if (deviceAge > 3 && deviceAge <= 5) {
            discount = 0.2;
        } else if (deviceAge > 5) {
            discount = 0.3;
        }

        // Áp dụng giảm giá dựa trên tình trạng của thiết bị
        if (status.equals("new")) {
            discount += 0.1;
        } else if (status.equals("old")) {
            discount += 0.2;
        } else if (status.equals("very old")) {
            discount += 0.3;
        }

        // Áp dụng giảm giá dựa trên vị trí hỏng của thiết bị
        if (damageLocation.equals("man hinh")) {
            discount += 0.1; // Nếu hỏng màn hình, giảm thêm 10%
        } else if (damageLocation.equals("pin")) {
            discount += 0.2; // Nếu hỏng pin, giảm thêm 20%
        } else if (damageLocation.equals("nguon")) {
            discount += 0.3; // Nếu hỏng nguồn, giảm thêm 30%
        }

        double estimatedPrice = initialPrice * (1 - discount);

        return estimatedPrice;
    }

    @Override
    public void saveQuoteRequest(RecycleRequest recycleRequest) {
        recycleRepository.save(recycleRequest);
    }
    public RecycleRequest saveRecycleRequest(RecycleRequest recycleRequest) {
        return recycleRepository.save(recycleRequest);
    }

    public Optional<RecycleRequest> getRecycleRequestById(Long id) {
        return recycleRepository.findById(id);
    }

    public RecycleRequest updateRecycleRequest(RecycleRequest recycleRequest) {
        return recycleRepository.save(recycleRequest);
    }
}
