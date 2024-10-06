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

    @NotBlank(message = "PersonId is required")
    private Long personId;

    @NotBlank(message = "Crypto is required")
    private CryptoCurrency crypto ;

    @NotBlank(message = "Quantity is required")
    private int quantity;

    @NotBlank(message = "Price is required")
    private Float price;

    @NotBlank(message = "Amount is required")
    private int AmountARG;

    @NotBlank(message = "Operation Type is required")
    private OperationType operationType;

}