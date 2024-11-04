package unq.cryptoexchange.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unq.cryptoexchange.models.enums.CryptoSymbol;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CryptoHolding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private CryptoSymbol cryptoSymbol; 
    
    @NotNull
    private Long personId;

    @Min(0)
    private int quantity; 

}
