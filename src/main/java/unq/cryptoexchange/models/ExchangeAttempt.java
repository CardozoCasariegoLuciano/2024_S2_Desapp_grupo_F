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
    private long personId;
    private String nameUser;
    private String lastNameUser;
    @Enumerated(EnumType.STRING)
    private CryptoCurrency crypto;
    private int cryptoQuantity;
    private Float price;
    private int amountARG;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private AttemptStatus status;
    @Enumerated(EnumType.STRING)
    private OperationType operationType;


    public ExchangeAttempt(
            CryptoCurrency crypto, 
            int cryptoQuantity, 
            Float price, 
            long personId, 
            String nameUser,
            String lastNameUser,
            int amountARG,
            OperationType operationType
            ) {
        this.crypto = crypto;
        this.cryptoQuantity = cryptoQuantity;
        this.price = price;
        this.personId = personId;
        this.nameUser = nameUser;
        this.lastNameUser = lastNameUser;
        this.amountARG = amountARG;
        this.status = AttemptStatus.OPEN;
        this.operationType = operationType;
        this.createdAt = LocalDateTime.now();
    }
}
