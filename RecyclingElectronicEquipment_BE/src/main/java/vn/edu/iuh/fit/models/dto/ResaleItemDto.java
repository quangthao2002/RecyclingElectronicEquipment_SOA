package vn.edu.iuh.fit.models.dto;

import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.models.DeviceStatus;

@Getter
@Setter
public class ResaleItemDto {
    private String quoteCode;
    private double totalRevenue;
    private int quantitySold;
    private String deviceType;
//    private String brand;
    private String model;
    private int deviceAge;
    private DeviceStatus deviceStatus;
}