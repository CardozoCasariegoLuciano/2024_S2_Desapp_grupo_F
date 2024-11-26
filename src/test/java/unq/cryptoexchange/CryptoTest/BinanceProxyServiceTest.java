package unq.cryptoexchange.CryptoTest;

import org.junit.jupiter.api.Test;
import unq.cryptoexchange.models.CryptoCurrency;
import unq.cryptoexchange.services.impl.BinanceProxyService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BinanceProxyServiceTest {
    private final BinanceProxyService binanceProxyService = new BinanceProxyService();

    @Test
    void test01_GetLast24HoursPrices() {

        String symbol = "BTCUSDT";

        List<CryptoCurrency> result = binanceProxyService.getLast24HoursPrices(symbol);

        assertNotNull(result, "The result should not be null.");
        assertFalse(result.isEmpty(), "The result list should not be empty.");
        assertEquals(24, result.size(), "The result should contain 24 entries.");

        CryptoCurrency firstCrypto = result.get(0);

        assertEquals("BTCUSDT", firstCrypto.getSymbol(), "The symbol should match the requested one.");
        assertNotNull(firstCrypto.getPrice(), "The price should not be null.");
        assertNotNull(firstCrypto.getLastUpdateDateAndTime(), "The timestamp should not be null.");
    }
}
