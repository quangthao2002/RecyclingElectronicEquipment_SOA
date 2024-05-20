package vn.edu.iuh.fit.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FinancialReportDto {
    private double totalRevenue;
    private double totalExpenses;
    private double netProfit;
}
