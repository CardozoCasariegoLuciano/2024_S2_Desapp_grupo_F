package unq.cryptoexchange.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unq.cryptoexchange.models.enums.AttemptStatus;
import unq.cryptoexchange.models.enums.OperationType;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeAttempt {
    private Long attemptId;
    private Long userId;
    private String crypto;
    private int quantity;
    private Float price;
    private Date createdAt;
    private AttemptStatus status = AttemptStatus.OPEN;

    private OperationType operationType;


    public ExchangeAttempt(Float price, int quantity, String crypto, Long userId, OperationType operationType) {
        this.price = price;
        this.quantity = quantity;
        this.crypto = crypto;
        this.userId = userId;
        this.operationType = operationType;
    }
}
