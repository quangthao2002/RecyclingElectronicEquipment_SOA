package vn.edu.iuh.fit.models.dto;

import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.models.RecyclingReceiptStatus;

@Getter
@Setter
public class StaffRecyclingReceiptDto {
    private Long id;
    private RecyclingReceiptStatus recyclingReceiptStatus;
    private String paymentMethod;
    private String paymentStatus;
    private UserDto user;
//    private StaffDto staff;
    private QuoteResponseDto quote;
    private DeviceDTO device;
}