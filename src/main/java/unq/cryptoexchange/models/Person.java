package unq.cryptoexchange.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unq.cryptoexchange.models.enums.AttemptStatus;
import unq.cryptoexchange.models.enums.CryptoCurrency;
import unq.cryptoexchange.models.enums.OperationType;

import java.util.ArrayList;
import java.util.List;

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

    @NotBlank
    @Size(min = 3, max = 30)
    private String name;

    @NotBlank
    @Size(min = 3, max = 30)
    private String lastname; 

    @Column(unique = true)
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min= 8, max=30)
    private String address;

    @JsonIgnore  
    @NotNull
    @Pattern(regexp ="^(?=.*[a-z])(?=.*[A-Z])(?=.*[@$!%*?&_-])[A-Za-z\\d@$!%*?&_-]{8,}$")
    private String password;
    
    @Min(0)
    private int reputation;

    @NotBlank
    @Size(min= 22)
    private String cvu;

    @NotBlank
    @Size(min= 8)
    private String wallet;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ExchangeAttempt> personAttempts = new ArrayList<>();

    public ExchangeAttempt createAttempt(CryptoCurrency crypto, int quantity, Float price, OperationType operationType){
        ExchangeAttempt exchangeAttempt = new ExchangeAttempt(price, quantity, crypto, this, operationType);
        personAttempts.add(exchangeAttempt);
        return exchangeAttempt;
    }

    public Notification buyCrypto(ExchangeAttempt attempt){
        attempt.setStatus(AttemptStatus.PENDING);
        return new Notification(attempt, this.id);
    }

    public Notification sellCrypto(ExchangeAttempt attempt){
        attempt.setStatus(AttemptStatus.PENDING);
        return new Notification(attempt, this.id);
    }

    public void discountReputation(int amount){
        this.reputation = Math.max( 0, this.reputation-amount);
    }

    public void increaseReputation(int amount){
        this.reputation += amount;
    }
}
