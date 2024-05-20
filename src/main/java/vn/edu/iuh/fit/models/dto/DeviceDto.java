package vn.edu.iuh.fit.models.dto;

import lombok.Data;
import vn.edu.iuh.fit.models.DeviceStatus;

/**
 * DTO for {@link vn.edu.iuh.fit.models.Device}
 */
@Data
public class DeviceDto {
    Long deviceId;
    String model;
    int deviceAge;
    DeviceStatus status;
    String damageLocation;
    String damageDescription;
}