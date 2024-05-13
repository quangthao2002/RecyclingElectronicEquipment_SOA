package vn.edu.iuh.fit.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class QuoteResponseDto {
    private int estimatedPrice;
    private String model;
    private  String productCode;
}