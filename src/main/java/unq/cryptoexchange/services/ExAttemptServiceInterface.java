package unq.cryptoexchange.services;

import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
//import unq.cryptoexchange.models.ExchangeAttempt;
import unq.cryptoexchange.models.ExchangeAttempt;


public interface ExAttemptServiceInterface {

    //ExchangeAttempt saveExchangeAttempt (ExchangeAttemptDto exchangeAttemptDtoDto);
    ExchangeAttempt publishExchange(ExchangeAttemptDto exchangeAttempt);
    void cleanAll();

}