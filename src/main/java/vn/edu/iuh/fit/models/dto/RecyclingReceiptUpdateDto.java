package vn.edu.iuh.fit.models.dto;

import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.models.RecyclingReceiptStatus;

@Getter @Setter
public class RecyclingReceiptUpdateDto {
    private Double finalQuotePrice;
    private RecyclingReceiptStatus recyclingReceiptStatus;
}
