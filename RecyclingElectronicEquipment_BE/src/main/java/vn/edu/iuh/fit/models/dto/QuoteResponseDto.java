package vn.edu.iuh.fit.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.models.QuoteStatus;
import vn.edu.iuh.fit.models.RecyclingReceiptStatus;

import java.time.LocalDate;

@Getter @Setter
public class QuoteResponseDto {
    private Long quoteId;
    private int estimatedPrice;
    private Double finalQuotePrice;
    private String model;
    private String productCode;
    private String deviceType;
    private QuoteStatus quoteStatus;
    private RecyclingReceiptStatus recyclingReceiptStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
}