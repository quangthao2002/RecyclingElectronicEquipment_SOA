package vn.edu.iuh.fit.services.Impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.models.Device;
import vn.edu.iuh.fit.repository.DeviceRepository;
import vn.edu.iuh.fit.services.IDeviceService;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl  implements IDeviceService {

    private final DeviceRepository deviceRepository;
    @Override
    public void saveDevice(Device device) {
            deviceRepository.save(device);
    }

    @Override
    public Device getDeviceById(Long id) {
        return deviceRepository.findById(id).orElse(null);
    }
}
