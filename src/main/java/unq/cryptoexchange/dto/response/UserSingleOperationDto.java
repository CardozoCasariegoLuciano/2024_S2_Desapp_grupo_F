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
    private int cryptoQuantity;
    private double currentValue;
    private double argTotal;
}
