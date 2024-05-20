package vn.edu.iuh.fit.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResaleItemDto {
    private Long productId;
    private String productName;
    private int quantitySold;
    private double unitPrice;
    private double totalRevenue;
}