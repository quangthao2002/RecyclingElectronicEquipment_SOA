package vn.edu.iuh.fit.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.models.RecyclingReceipt;
import vn.edu.iuh.fit.models.RecyclingReceiptStatus;
import vn.edu.iuh.fit.models.dto.DeviceRequestDto;
import vn.edu.iuh.fit.repository.RecycleRepository;

import vn.edu.iuh.fit.services.IRecycleRequestService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecycleRequestImpl implements IRecycleRequestService {

    private final RecycleRepository recycleRepository;

    @Override
    public double calculateEstimatedPrice(DeviceRequestDto deviceRequestDto) {
        double initialPrice = deviceRequestDto.getInitialPrice();
        int deviceAge = deviceRequestDto.getDeviceAge();
        String deviceStatus = deviceRequestDto.getDeviceStatus();
        String damageLocation = deviceRequestDto.getDamageLocation();

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
        if (deviceStatus.equals("new")) {
            discount += 0.1;
        } else if (deviceStatus.equals("old")) {
            discount += 0.2;
        } else if (deviceStatus.equals("very old")) {
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
    public void saveQuoteRequest(RecyclingReceipt recycleRequest) {
        recycleRepository.save(recycleRequest);
    }

    @Override
    public List<RecyclingReceipt> getRecyclingReceiptsByStatus(RecyclingReceiptStatus status) {
        return recycleRepository.getRecyclingReceiptsByRecyclingReceiptStatus(status);
    }

    @Override
    public List<RecyclingReceipt> getAllRecyclingReceipts() {
        return  recycleRepository.findAll();
    }

    public RecyclingReceipt saveRecyclingReceipt(RecyclingReceipt recyclingReceipt) {
        return recycleRepository.save(recyclingReceipt);
    }

    public RecyclingReceipt getRecycleRequestById(Long id) {
        return  recycleRepository.findById(id).orElse(null);
    }

    public RecyclingReceipt updateRecycleRequest(RecyclingReceipt recycleRequest) {
        return recycleRepository.save(recycleRequest);
    }
}
