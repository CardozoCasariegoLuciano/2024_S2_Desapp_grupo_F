package unq.cryptoexchange.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unq.cryptoexchange.models.CryptoCurrency;
import unq.cryptoexchange.services.CryptoPriceServiceInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CryptoPriceService implements CryptoPriceServiceInterface {

    @Autowired
    private BinanceProxyService binanceProxyService;

    public CryptoCurrency getPrice(String symbol) {
        CryptoCurrency cryptoCurrency = binanceProxyService.getCryptoPrice(symbol);
        cryptoCurrency.setLastUpdateDateAndTime(LocalDateTime.now().toString());

        return cryptoCurrency;
    }

    public List<CryptoCurrency> getAllPrices(List<String> symbols) {
        List<CryptoCurrency> cryptoPrices = new ArrayList<>();

        for (String symbol : symbols) {
            CryptoCurrency cryptoCurrency = binanceProxyService.getCryptoPrice(symbol);
            cryptoCurrency.setLastUpdateDateAndTime(LocalDateTime.now().toString());
            cryptoPrices.add(cryptoCurrency);
        }

        return cryptoPrices;
    }
}