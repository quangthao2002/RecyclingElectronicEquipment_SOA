package vn.edu.iuh.fit.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperationalReportDto {
    private int totalDevicesRecycled;
    private double recyclingRate;
    private double averageProcessingTime;

    public void setTotalExpenses(double totalExpenses) {
    }
}
