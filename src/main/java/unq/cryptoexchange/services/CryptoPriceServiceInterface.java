package unq.cryptoexchange.services;

import unq.cryptoexchange.models.CryptoCurrency;

import java.util.List;

public interface CryptoPriceServiceInterface {

    CryptoCurrency getPrice(String symbol);

    List<CryptoCurrency> getAllPrices(List<String> symbols);

}
