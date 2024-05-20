package vn.edu.iuh.fit.models.dto;


import lombok.Data;
import vn.edu.iuh.fit.models.QuoteStatus;

@Data
public class QuoteDto {
    private Long quoteId;
    private double firstQuotePrice;
    private QuoteStatus quoteStatus;
    private DeviceDto device;
}
