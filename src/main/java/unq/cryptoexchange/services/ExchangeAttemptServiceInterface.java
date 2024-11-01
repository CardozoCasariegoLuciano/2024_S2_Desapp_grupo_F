package unq.cryptoexchange.services;

import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
import unq.cryptoexchange.dto.request.ItemExAttemptDto;
import unq.cryptoexchange.models.ExchangeAttempt;
import java.util.List;

public interface ExchangeAttemptServiceInterface {

    ExchangeAttempt saveExchangeAttempt(ExchangeAttemptDto exAttemptDto);
    boolean priceInMargin(Float userPrice, Float cryptoPrice);
    List<ItemExAttemptDto> getAllExchangeAttempt();
    int countOperationUserId(Long personId);
    void cleanAll();

}