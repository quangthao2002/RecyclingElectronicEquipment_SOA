package vn.edu.iuh.fit.models.dto;

import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.models.DeviceStatus;
import vn.edu.iuh.fit.models.QuoteStatus;

@Getter @Setter
public class DeviceRequestDto {
    private double initialPrice;
    private String model;
    private int deviceAge;
    private QuoteStatus quoteStatus;
    private DeviceStatus deviceStatus;
    private Double finalQuotePrice ;
    private String productCode;
    private String damageLocation;
    private String damageDescription;
}