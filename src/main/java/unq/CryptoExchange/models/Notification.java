package unq.CryptoExchange.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    private Date date;
    private ExchangeAttempt exchangeAttempt;
    private Long requestingUser_id;

    public Notification(ExchangeAttempt attempt, Long personId) {
        this.date = new Date();
        this.exchangeAttempt = attempt;
        this.requestingUser_id = personId;
    }
}
