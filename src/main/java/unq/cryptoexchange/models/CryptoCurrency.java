package unq.cryptoexchange.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CryptoCurrency {

    @Id
    private String symbol;
    private Float price;
    private String lastUpdateDateAndTime;

}
