package vn.edu.iuh.fit.models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecycleRequestDto {
    private String productCode;
    private int deviceAge;
    private String status;
    private String damageLocation;
    private String damageDescription;
    private String fullName;
    private String address;
    private String phoneNumber;
    private String gender;
    private String email;
}
