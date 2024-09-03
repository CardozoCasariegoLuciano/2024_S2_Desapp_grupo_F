package unq.CryptoExchange.models;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
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

    public SellingAttempt createSellingAttempt(String cripto, int quantity, Float price){
        return new SellingAttempt(price,quantity, cripto, this.person_id);
    }
}
