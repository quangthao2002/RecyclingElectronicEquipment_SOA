package vn.edu.iuh.fit.models.dto;


import lombok.Data;
import vn.edu.iuh.fit.models.QuoteStatus;

@Data
public class QuoteDTO {
    private Long quoteId;
    private double firstQuotePrice;
    private QuoteStatus quoteStatus;
    private DeviceDTO device;
}
