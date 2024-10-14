package unq.cryptoexchange.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unq.cryptoexchange.models.enums.CryptoCurrency;
import unq.cryptoexchange.models.enums.OperationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeAttemptDto {

    @NotBlank(message = "Person Id is required")
    private Long personId;

    @NotBlank(message = "Name is required")
    @Size(message = "The name must be between 3 and 30 characters", max = 30, min = 3)
    private String name;

    @NotBlank(message = "LastName is required")
    @Size(message = "The name must be between 3 and 30 characters", max = 30, min = 3)
    private String lastName;

    @NotBlank(message = "Crypto is required")
    private CryptoCurrency crypto;

    @NotBlank(message = "Quantity is required")
    @Min(value=1, message = "The quantity must be equal to or greater than 1")
    private int quantity;

    @NotBlank(message = "Price is required")
    private Float price;

    @NotBlank(message = "Type Operation is required")
    private OperationType operationType;
    
}