package vn.edu.iuh.fit.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.models.QuoteStatus;
import vn.edu.iuh.fit.models.RecyclingReceiptStatus;

import java.time.LocalDate;

@Getter @Setter 
public class QuoteResponseDto {
    private Long recyclingReceiptId;
    private Long quoteId;
    private Long deviceId;
    private int estimatedPrice;
    private Double finalQuotePrice;
    private String model;
    private String productCode;
    private String deviceType;
    private QuoteStatus quoteStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;


    public void setRecyclingReceiptStatus(RecyclingReceiptStatus recyclingReceiptStatus) {
    }
}