package vn.edu.iuh.fit.models.dto;

import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.models.QuoteStatus;

import java.time.LocalDateTime;

@Getter @Setter
public class QuoteResponseDto {
    private Long quoteId;
    private int estimatedPrice;
    private Double finalQuotePrice;
    private String model;
    private String productCode;
    private QuoteStatus quoteStatus;
    private LocalDateTime createdAt;
}