package vn.edu.iuh.fit.models.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link vn.edu.iuh.fit.models.Device}
 */
@Data
public class DeviceDTO  {
    Long deviceId;
    String model;
    int deviceAge;
    String status;
    String damageLocation;
    String damageDescription;
}