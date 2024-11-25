package unq.cryptoexchange.services.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import unq.cryptoexchange.models.CryptoCurrency;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class BinanceProxyService {

    private static final String BASE_URL = "https://api1.binance.com/api/v3/ticker/price?symbol=";
    private static final String KLINES_URL = "https://api1.binance.com/api/v3/klines";
    private final RestTemplate restTemplate;

    public BinanceProxyService() {
        this.restTemplate = new RestTemplate();
    }


    public CryptoCurrency getCryptoPrice(String symbol) {
        String url = BASE_URL + symbol;
        return restTemplate.getForObject(url, CryptoCurrency.class);
    }



    public List<CryptoCurrency> getLast24HoursPrices(String symbol) {
        String url = KLINES_URL + "?symbol=" + symbol + "&interval=1h&limit=24";
        Object[][] klines = restTemplate.getForObject(url, Object[][].class);

        List<CryptoCurrency> priceHistoryList = new ArrayList<>();
        for (Object[] kline : klines) {
            long timestamp = ((Number) kline[0]).longValue();
            double closePrice = Double.parseDouble(kline[4].toString());

            CryptoCurrency cryptoCurrency = CryptoCurrency.builder()
                    .symbol(symbol)
                    .price((float) closePrice)
                    .lastUpdateDateAndTime(Instant.ofEpochMilli(timestamp).toString())
                    .build();

            priceHistoryList.add(cryptoCurrency);
        }

        return priceHistoryList;
    }

}
