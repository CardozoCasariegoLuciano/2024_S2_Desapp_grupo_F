package unq.CryptoExchange.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    @Size(min = 8)
    @Email
    private String email;

    @JsonIgnore  
    @NotNull
    private String password;
        
    @Min(0)
    private int reputation;
    
    private String cvu;
    private String name;
    private String lastname;  
    private String street;
    private String wallet;


    public ExchangeAttempt createAttempt(String crypto, int quantity, Float price, OperationType operationType){
        return new ExchangeAttempt(price,quantity, crypto, this.id, operationType);
    }

    public Notification buyCrypto(ExchangeAttempt attempt){
        attempt.setStatus(AttemptStatus.PENDING);
        return new Notification(attempt, this.id);
    }


}
