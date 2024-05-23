package vn.edu.iuh.fit.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.iuh.fit.models.PaymentStatus;
import vn.edu.iuh.fit.models.RecyclingReceiptStatus;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor

public class RecyclingReceiptDto {
    private Long userId;
    private Long quoteId;
    private String paymentMethod;
    private RecyclingReceiptStatus recyclingReceiptStatus;
    private PaymentStatus paymentStatus;
    private DeviceDto device;
    private QuoteDto quote;
}

