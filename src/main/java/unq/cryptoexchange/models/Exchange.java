package unq.cryptoexchange.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import unq.cryptoexchange.models.enums.AttemptStatus;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Exchange {

    private LocalDateTime date;
    private ExchangeAttempt exchangeAttempt;
    private Person requestingUser;

    public Exchange(ExchangeAttempt attempt, Person requestingUser) {
        this.date = LocalDateTime.now();
        this.exchangeAttempt = attempt;
        this.requestingUser = requestingUser;
    }

    public void makeTransfer(){
        this.exchangeAttempt.setStatus(AttemptStatus.PENDING);

    }

    public void confrirmReception(){
        this.exchangeAttempt.setStatus(AttemptStatus.CLOSE);
    }

    public void cancelTransaction(Person cancellingUser){
        this.exchangeAttempt.setStatus(AttemptStatus.CANCELLED);
        cancellingUser.discountPoints(20);
    }
}
