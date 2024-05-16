package vn.edu.iuh.fit.services;

import vn.edu.iuh.fit.models.Device;

public interface IDeviceService {
    void saveDevice(Device device);
    Device getDeviceById(Long id);
}
