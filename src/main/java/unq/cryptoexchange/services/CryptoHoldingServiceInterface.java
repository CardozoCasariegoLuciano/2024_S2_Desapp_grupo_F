package unq.cryptoexchange.services;

import unq.cryptoexchange.models.enums.CryptoSymbol;

public interface CryptoHoldingServiceInterface {

    boolean personHaveThisCant(Long personId, CryptoSymbol crypto, int cant);
    void cleanAll();

}