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

    public boolean priceInMargin(Float userPrice) {

        float marginPrice = price * 0.05f;

        float maxPrice = price + marginPrice;
        float minPrice = price - marginPrice;

        return userPrice >= minPrice && userPrice <= maxPrice;
    }
}
