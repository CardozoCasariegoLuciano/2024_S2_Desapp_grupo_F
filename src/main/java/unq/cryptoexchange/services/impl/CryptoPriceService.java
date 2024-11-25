package unq.cryptoexchange.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
import unq.cryptoexchange.models.CryptoCurrency;
import unq.cryptoexchange.models.ExchangeAttempt;
import unq.cryptoexchange.services.CryptoPriceServiceInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoPriceService implements CryptoPriceServiceInterface {

    @Autowired
    private BinanceProxyService binanceProxyService;

    @Cacheable(value = "cryptoPrice", key = "#symbol")
    public CryptoCurrency getPrice(String symbol) {
        CryptoCurrency cryptoCurrency = binanceProxyService.getCryptoPrice(symbol);
        cryptoCurrency.setLastUpdateDateAndTime(LocalDateTime.now().toString());

        return cryptoCurrency;
    }

    @Cacheable(value = "allCryptoPrices", key = "#symbol")
    public List<CryptoCurrency> getAllPrices(List<String> symbols) {
        List<CryptoCurrency> cryptoPrices = new ArrayList<>();

        for (String symbol : symbols) {
            CryptoCurrency cryptoCurrency = binanceProxyService.getCryptoPrice(symbol);
            cryptoCurrency.setLastUpdateDateAndTime(LocalDateTime.now().toString());
            cryptoPrices.add(cryptoCurrency);
        }

        return cryptoPrices;
    }

    @Cacheable(value = "cypto24hPrices", key = "#symbol")
    public List<CryptoCurrency> getLast24HoursPrices(String symbol) {
        return binanceProxyService.getLast24HoursPrices(symbol);
    }

    public boolean isPriceInRange(ExchangeAttempt attemp){
        CryptoCurrency currentCryptoPrice = this.getPrice(attemp.getCrypto().name());
        Float exchangeValue = attemp.getPrice();

        return currentCryptoPrice.priceInMargin(exchangeValue);
    }

    public boolean isPriceInRange(ExchangeAttemptDto attemp){
        CryptoCurrency currentCryptoPrice = this.getPrice(attemp.getCrypto().name());
        Float exchangeValue = attemp.getPrice();

        return currentCryptoPrice.priceInMargin(exchangeValue);
    }
}