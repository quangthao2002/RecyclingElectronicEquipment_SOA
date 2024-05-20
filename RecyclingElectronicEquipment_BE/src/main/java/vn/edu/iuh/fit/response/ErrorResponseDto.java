package vn.edu.iuh.fit.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ErrorResponseDto {
    private String message;
    private String errorCode;

}
