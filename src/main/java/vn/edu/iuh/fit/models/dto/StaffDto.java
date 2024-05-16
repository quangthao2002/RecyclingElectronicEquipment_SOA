package vn.edu.iuh.fit.models.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link vn.edu.iuh.fit.models.Staff}
 */
@Data
public class StaffDto implements Serializable {
    String name;
    String email;
}