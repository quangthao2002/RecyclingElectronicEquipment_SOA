package vn.edu.iuh.fit.models.dto;


import lombok.Data;
import vn.edu.iuh.fit.models.QuoteStatus;
import vn.edu.iuh.fit.models.RecyclingReceiptStatus;

@Data
public class QuoteDto {
    private Long quoteId;
    private double firstQuotePrice;
    private QuoteStatus quoteStatus;
    private DeviceDto device;
    private RecyclingReceiptStatus recyclingReceiptStatus;
}
