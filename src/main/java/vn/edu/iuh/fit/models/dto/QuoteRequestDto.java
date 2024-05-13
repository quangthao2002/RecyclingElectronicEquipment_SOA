package vn.edu.iuh.fit.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QuoteRequestDto {
    private double initialPrice;
    private String model;
    private int deviceAge;
    private String status;
    private String damageLocation;
    private String damageDescription;
    private int estimatedPrice;
//    private String imageDevice;
}