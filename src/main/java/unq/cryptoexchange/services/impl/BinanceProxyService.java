package unq.cryptoexchange.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import unq.cryptoexchange.models.CryptoCurrency;

@Service
public class BinanceProxyService {

    private final String BASE_URL = "https://api1.binance.com/api/v3/ticker/price?symbol=";
    private final RestTemplate restTemplate;

    public BinanceProxyService() {
        this.restTemplate = new RestTemplate();
    }

    public CryptoCurrency getCryptoPrice(String symbol) {
        String url = BASE_URL + symbol;
        return restTemplate.getForObject(url, CryptoCurrency.class);
    }

}
