package unq.cryptoexchange.services;

import unq.cryptoexchange.models.ExchangeAttempt;
import unq.cryptoexchange.dto.request.ExchangeAttemptDto;

public interface ExchangeAttemptServiceInterface {

    ExchangeAttempt saveExchangeAttempt(ExchangeAttemptDto exAttemptDto);
    void cleanAll();

}