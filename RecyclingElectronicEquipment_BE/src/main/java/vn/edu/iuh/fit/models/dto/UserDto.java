package vn.edu.iuh.fit.models.dto;

import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link vn.edu.iuh.fit.models.User}
 */
@Data
public class UserDto  {
    Long id;
    private String fullName;
    String email;
    String address;
    String phoneNumber;
}