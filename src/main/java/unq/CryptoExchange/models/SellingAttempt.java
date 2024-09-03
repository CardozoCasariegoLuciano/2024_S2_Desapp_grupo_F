package unq.CryptoExchange.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellingAttempt {
    private Long sellingAttempt_id;
    private Long seller_id;
    private String cripto;
    private int quantity;
    private Float price;
    private Date created_at;
    private AttemptStatus status = AttemptStatus.OPEN;


    public SellingAttempt(Float price, int quantity, String cripto, Long seller_id) {
        this.price = price;
        this.quantity = quantity;
        this.cripto = cripto;
        this.seller_id = seller_id;
    }
}
