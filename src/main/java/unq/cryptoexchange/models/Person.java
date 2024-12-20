package unq.cryptoexchange.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;
import unq.cryptoexchange.models.enums.CryptoSymbol;
import unq.cryptoexchange.models.enums.OperationType;

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
    private String password;
    
    @Min(0)
    @Default
    private Integer points = 100; 

    @NotBlank
    @Size(min= 22)
    private String cvu;

    @NotBlank
    @Size(min= 8)
    private String wallet;
 
    public ExchangeAttempt createAttempt(CryptoSymbol crypto, int quantity, Float price, OperationType operationType){
        return new ExchangeAttempt(price, quantity, crypto, this.id, this.name, this.lastname, operationType);
    }

    public void discountPoints(int amount){
        this.points = Math.max( 0, this.points-amount);
    }

    public void increasePoints(int amount){
        this.points = Math.min( 300, this.points+amount);
    }

    public String getReputation(int cantOp){

        String reputation = "Sin Operaciones";

        if(cantOp != 0){
            int calcuteReputation = this.points/cantOp;
            reputation = String.valueOf(calcuteReputation) ;
        }

        return reputation;
    }

}
