package unq.cryptoexchange.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unq.cryptoexchange.models.enums.CryptoSymbol;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemExAttemptDto {

    private LocalDateTime createdAt;
    private CryptoSymbol crypto;
    private int cryptoQuantity;
    private Float price;
    private Float amountARG;
    private String userName;
    private String userLastname;
    private int userOperationsCount;
    private int userReputation;    

}