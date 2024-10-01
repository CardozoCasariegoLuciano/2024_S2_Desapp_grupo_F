package unq.cryptoexchange.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unq.cryptoexchange.models.enums.AttemptStatus;
import unq.cryptoexchange.models.enums.CryptoCurrency;
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
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;
    @Enumerated(EnumType.STRING)
    private CryptoCurrency crypto;
    private int cryptoQuantity;
    private Float price;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private AttemptStatus status = AttemptStatus.OPEN;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;


    public ExchangeAttempt(Float price, int cryptoQuantity, CryptoCurrency crypto, Person person, OperationType operationType) {
        this.price = price;
        this.cryptoQuantity = cryptoQuantity;
        this.crypto = crypto;
        this.person = person;
        this.createdAt = LocalDateTime.now();
        this.operationType = operationType;
    }
}
