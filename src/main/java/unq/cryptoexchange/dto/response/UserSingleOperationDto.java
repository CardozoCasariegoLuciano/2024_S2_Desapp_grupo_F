package unq.cryptoexchange.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unq.cryptoexchange.models.enums.CryptoSymbol;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSingleOperationDto {
    private CryptoSymbol crypto;
    private int crypto_quantity;
    private double current_value;
    private double arg_total;
}
