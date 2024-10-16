package unq.cryptoexchange.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import unq.cryptoexchange.models.CryptoCurrency;
import unq.cryptoexchange.services.impl.CryptoPriceService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/crypto")
public class CyptoController {
    @Autowired
    private CryptoPriceService cryptoPriceService;

    @GetMapping("/price/{symbol}")
    public CryptoCurrency getCryptoPrice(@PathVariable String symbol) {
        return cryptoPriceService.getPrice(symbol);
    }

    @GetMapping("/prices")
    public List<CryptoCurrency> getAllCryptoPrices() {
        List<String> symbols = Arrays.asList(
                "ALICEUSDT", "MATICUSDT", "AXSUSDT", "AAVEUSDT", "ATOMUSDT",
                "NEOUSDT", "DOTUSDT", "ETHUSDT", "CAKEUSDT", "BTCUSDT",
                "BNBUSDT", "ADAUSDT", "TRXUSDT", "AUDIOUSDT"
        );

        return cryptoPriceService.getAllPrices(symbols);
    }
}
