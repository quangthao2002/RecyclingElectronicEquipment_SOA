package vn.edu.iuh.fit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.edu.iuh.fit.models.Device;


public interface DeviceRepository extends JpaRepository<Device, Long> {
}