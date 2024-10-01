package unq.cryptoexchange.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unq.cryptoexchange.models.ExchangeAttempt;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonRegistrationDto {
    @NotBlank(message = "Name is required")
    @Size(message = "The name must be between 3 and 30 characters", max = 30, min = 3)
    private String name;

    @NotBlank(message = "Lastname is required")
    @Size(message = "The lastname must be between 3 and 30 characters", max = 30, min = 3)
    private String lastname;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Address is required")
    @Size(message = "Address should be valid",min= 8, max=30)
    private String address;

    @NotBlank(message = "Password is required")
    @Size(message = "The password must be between 8 and 20 characters", max = 20, min = 8)
    @Pattern(message = "The password must have at least 1 lowercase, 1 uppercase, 1 special character and at least 8 characters",regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&_-])[A-Za-z\\d@$!%*?&_-]{8,}$")
    private String password;

    @NotBlank(message = "CVU is required")
    @Size(message = "CVU  must have at least 22 character",min= 22)
    private String cvu;
    
    @NotBlank(message = "Wallet is required")
    @Size(message = "Wallet must have at least 8 character",min= 8)
    private String wallet;

    private List<ExchangeAttempt> personAttempts = new ArrayList<>();
}