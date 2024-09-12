package unq.CryptoExchange.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unq.CryptoExchange.models.enums.AttemptStatus;
import unq.CryptoExchange.models.enums.OperationType;

@Entity
@Table(name = "persons")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person {

    @id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @unique
    @NotNull
    @Size(min = 8)
    @Email
    private String email;

    @JsonIgnore  
    @NotNull
    private String password;
        
    @Min(0)
    private int reputation = 5;
    
    private String cvu;
    private String name;
    private String lastname;  
    private String street;
    private String wallet;


    public ExchangeAttempt createAttempt(String crypto, int quantity, Float price, OperationType operationType){
        return new ExchangeAttempt(price,quantity, crypto, this.person_id, operationType);
    }

    public Notification buyCrypto(ExchangeAttempt attempt){
        attempt.setStatus(AttemptStatus.PENDING);
        return new Notification(attempt, this.person_id);
    }


}
