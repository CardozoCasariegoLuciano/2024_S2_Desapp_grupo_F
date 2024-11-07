package unq.cryptoexchange.services;

import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
import unq.cryptoexchange.dto.request.ItemExAttemptDto;
import unq.cryptoexchange.models.ExchangeAttempt;
import java.util.List;

public interface ExchangeAttemptServiceInterface {

    ExchangeAttempt findExchangeAttemp(Long id);
    ExchangeAttempt saveExchangeAttempt(ExchangeAttemptDto exAttemptDto);
    List<ItemExAttemptDto> getAllExchangeAttempt();
    void cleanAll();

    void acceptAttemp(Long attempID, Long userID);

    void confirmAttemp(Long attempID, Long userID);

    void cancelAttemp(Long attempID, Long userID);
}