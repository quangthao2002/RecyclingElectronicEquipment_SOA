package vn.edu.iuh.fit.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.models.DeviceStatus;
import vn.edu.iuh.fit.models.RecyclingReceipt;
import vn.edu.iuh.fit.models.RecyclingReceiptStatus;
import vn.edu.iuh.fit.models.dto.DeviceRequestDto;
import vn.edu.iuh.fit.repository.RecyclingRepository;

import vn.edu.iuh.fit.services.IRecycleRequestService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecycleRequestImpl implements IRecycleRequestService {

    private final RecyclingRepository recycleRepository;
    @Override
    public double calculateEstimatedPrice(DeviceRequestDto deviceRequestDto) {
        // Lấy giá trị thiết bị dựa trên model từ database hoặc một nguồn khác
        double initialPrice = deviceRequestDto.getInitialPrice();

        int deviceAge = deviceRequestDto.getDeviceAge();
        DeviceStatus deviceStatus = deviceRequestDto.getDeviceStatus();
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
        switch (deviceStatus) {
            case NEW -> discount += 0.1;
            case USED -> discount += 0.2;
            case DAMAGED -> discount += 0.3;
            case BROKEN -> discount += 0.5; // Giảm 50% nếu thiết bị hỏng
        }

        // Áp dụng giảm giá dựa trên vị trí hỏng của thiết bị
        if (damageLocation.equals("man hinh")) {
            discount += 0.1;
        } else if (damageLocation.equals("pin")) {
            discount += 0.2;
        } else if (damageLocation.equals("nguon")) {
            discount += 0.3;
        }

        // Giới hạn discount tối đa là 90%
        discount = Math.min(discount, 0.9);

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

    @Override
    public List<RecyclingReceipt> findByRecyclingReceiptStatusIn(List<RecyclingReceiptStatus> statuses) {
        return recycleRepository.findByRecyclingReceiptStatusIn(statuses );
    }

    @Override
    public List<RecyclingReceipt> findByRecyclingReceiptsByStatus(RecyclingReceiptStatus status) {
        return recycleRepository.findByRecyclingReceiptStatus(status);
    }
}
