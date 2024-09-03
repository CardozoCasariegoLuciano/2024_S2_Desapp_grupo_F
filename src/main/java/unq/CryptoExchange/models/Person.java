package unq.CryptoExchange.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unq.CryptoExchange.models.enums.AttemptStatus;
import unq.CryptoExchange.models.enums.OperationType;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {
    private Long person_id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private String cvu;
    private String street;
    private String wallet;
    private int reputation;

    public ExchangeAttempt createAttempt(String crypto, int quantity, Float price, OperationType operationType){
        return new ExchangeAttempt(price,quantity, crypto, this.person_id, operationType);
    }

    public Notification buyCrypto(ExchangeAttempt attempt){
        attempt.setStatus(AttemptStatus.PENDING);
        return new Notification(attempt, this.person_id);
    }


}
