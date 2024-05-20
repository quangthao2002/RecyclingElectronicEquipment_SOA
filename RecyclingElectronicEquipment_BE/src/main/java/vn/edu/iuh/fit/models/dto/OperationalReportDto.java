package vn.edu.iuh.fit.models.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class OperationalReportDto {
    private int totalRecycledDevices;
    private Map<String, Integer> devicesByType;
    private double averageRecyclingTime;
}
