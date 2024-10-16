package unq.cryptoexchange.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unq.cryptoexchange.models.enums.AttemptStatus;
import unq.cryptoexchange.models.enums.CryptoSymbol;
import unq.cryptoexchange.models.enums.OperationType;

import java.time.LocalDateTime;


@Entity
@Table(name = "exchange_attempts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExchangeAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attemptId;
    private Long personId;
    private String nameUser;
    private String lastNameUser;
    @Enumerated(EnumType.STRING)
    private CryptoSymbol crypto;
    private int cryptoQuantity;
    private Float price;
    private Float amountArg;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private AttemptStatus status;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;


    public ExchangeAttempt(Float price, int cryptoQuantity, CryptoSymbol crypto, Long personId, String nameUser, String lastNameUser, OperationType operationType) {
        this.price = price;
        this.cryptoQuantity = cryptoQuantity;
        this.crypto = crypto;
        this.amountArg = cryptoQuantity * price;
        this.personId = personId;
        this.nameUser = nameUser;
        this.lastNameUser = lastNameUser;
        this.status = AttemptStatus.OPEN;
        this.createdAt = LocalDateTime.now();
        this.operationType = operationType;
    }
}
