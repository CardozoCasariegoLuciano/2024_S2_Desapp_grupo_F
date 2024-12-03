package unq.cryptoexchange.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonLoginDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(message = "The password must be between 8 and 20 characters", max = 20, min = 8)
    @Pattern(message = "The password must have at least 1 lowercase, 1 uppercase, 1 special character and at least 8 characters",regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&_-])[A-Za-z\\d@$!%*?&_-]{8,}$")
    private String password;

}
