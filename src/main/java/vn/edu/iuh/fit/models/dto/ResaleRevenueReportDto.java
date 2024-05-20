package vn.edu.iuh.fit.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ResaleRevenueReportDto {
    private double totalResaleRevenue;
    private List<ResaleItemDto> resaleItem;
}
