package unq.CryptoExchange.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unq.CryptoExchange.models.enums.AttemptStatus;
import unq.CryptoExchange.models.enums.OperationType;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeAttempt {
    private Long attempt_id;
    private Long user_id;
    private String crypto;
    private int quantity;
    private Float price;
    private Date created_at;
    private AttemptStatus status = AttemptStatus.OPEN;

    private OperationType operationType;


    public ExchangeAttempt(Float price, int quantity, String crypto, Long user_id, OperationType operationType) {
        this.price = price;
        this.quantity = quantity;
        this.crypto = crypto;
        this.user_id = user_id;
        this.operationType = operationType;
    }
}
