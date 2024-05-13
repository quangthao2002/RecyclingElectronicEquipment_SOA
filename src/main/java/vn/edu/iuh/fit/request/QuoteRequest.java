package vn.edu.iuh.fit.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuoteRequest {

    private String model;
    private int deviceAge;
    private double initialPrice;
    private String status;
    private String damageLocation;
    private String damageDescription;

}

