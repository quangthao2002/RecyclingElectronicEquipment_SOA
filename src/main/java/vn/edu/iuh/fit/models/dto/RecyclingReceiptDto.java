package vn.edu.iuh.fit.models.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor

public class RecyclingReceiptDto {
    private Long userId;
    private Long quoteId;
    private String paymentMethod;
    private String paymentStatus;
}

