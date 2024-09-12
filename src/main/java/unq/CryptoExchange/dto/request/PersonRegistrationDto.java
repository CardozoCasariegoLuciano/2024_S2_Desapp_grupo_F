package unq.CryptoExchange.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonRegistrationDto {
    
    @NotBlank(message = "Name is required")
    @Size(message = "The name must be between 3 and 20 characters", max = 20, min = 3)
    private String name;

    @NotBlank(message = "Lastname is required")
    @Size(message = "The lastname must be between 3 and 20 characters", max = 20, min = 3)
    private String lastname;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(message = "The password must be between 8 and 20 characters", max = 20, min = 8)
    private String password;

    /*public Person toModel(){
        return new Person(null, this.name, this.lastname, this.email, this.password);
    }*/

}