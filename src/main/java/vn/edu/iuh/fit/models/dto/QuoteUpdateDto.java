package vn.edu.iuh.fit.models.dto;

import lombok.Getter;
import lombok.Setter;
import vn.edu.iuh.fit.models.QuoteStatus;

@Getter
@Setter
public class QuoteUpdateDto {
    private Double finalQuotePrice;
    private QuoteStatus quoteStatus;
}
