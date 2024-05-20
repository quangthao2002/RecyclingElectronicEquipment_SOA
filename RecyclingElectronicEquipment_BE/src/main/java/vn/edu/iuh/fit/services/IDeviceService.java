package vn.edu.iuh.fit.services;

import vn.edu.iuh.fit.models.Device;

public interface IDeviceService {
    Device saveDevice(Device device);
    Device getDeviceById(Long id);
}
