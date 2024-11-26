package unq.cryptoexchange.CryptoTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
import unq.cryptoexchange.models.CryptoCurrency;
import unq.cryptoexchange.models.enums.CryptoSymbol;
import unq.cryptoexchange.services.impl.BinanceProxyService;
import unq.cryptoexchange.services.impl.CryptoPriceService;

import java.util.Arrays;
import java.util.List;

class CryptoPriceServiceTest {

    @Mock
    private BinanceProxyService binanceProxyService;

    @InjectMocks
    private CryptoPriceService cryptoPriceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void test_01_getPrice() {
        String symbol = "BTCUSDT";
        CryptoCurrency mockCrypto = new CryptoCurrency(symbol, 65000.50f, "2024-01-01T12:00:00");

        when(binanceProxyService.getCryptoPrice(symbol)).thenReturn(mockCrypto);

        CryptoCurrency result = cryptoPriceService.getPrice(symbol);

        assertNotNull(result);
        assertEquals(symbol, result.getSymbol());
        assertEquals(65000.50f, result.getPrice());
        assertNotNull(result.getLastUpdateDateAndTime());
    }

    @Test
    void test_02_getAllPrices() {
        List<String> symbols = Arrays.asList("BTCUSDT", "ETHUSDT");
        List<CryptoCurrency> mockPrices = Arrays.asList(
                new CryptoCurrency("BTCUSDT", 65000.50f, "2024-01-01T12:00:00"),
                new CryptoCurrency("ETHUSDT", 1800.75f, "2024-01-01T12:05:00")
        );

        when(binanceProxyService.getCryptoPrice("BTCUSDT")).thenReturn(mockPrices.get(0));
        when(binanceProxyService.getCryptoPrice("ETHUSDT")).thenReturn(mockPrices.get(1));

        List<CryptoCurrency> result = cryptoPriceService.getAllPrices(symbols);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("BTCUSDT", result.get(0).getSymbol());
        assertEquals(65000.50f, result.get(0).getPrice());
        assertEquals("ETHUSDT", result.get(1).getSymbol());
        assertEquals(1800.75f, result.get(1).getPrice());
    }

    @Test
    void test_03_getLast24HoursPrices() {
        String symbol = "BTCUSDT";
        List<CryptoCurrency> mockHistory = Arrays.asList(
                new CryptoCurrency(symbol, 65000.0f, "2024-01-01T12:00:00"),
                new CryptoCurrency(symbol, 65200.0f, "2024-01-01T13:00:00")
        );

        when(binanceProxyService.getLast24HoursPrices(symbol)).thenReturn(mockHistory);

        List<CryptoCurrency> result = cryptoPriceService.getLast24HoursPrices(symbol);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(65000.0f, result.get(0).getPrice());
        assertEquals(65200.0f, result.get(1).getPrice());
    }

    @Test
    void test_04_isPriceInRange_ExchangeAttemptDto() {
        ExchangeAttemptDto exchangeAttempt = ExchangeAttemptDto.builder()
                .crypto(CryptoSymbol.BTCUSDT)
                .price(65000.0f)
                .build();

        CryptoCurrency mockCrypto = new CryptoCurrency("BTCUSDT", 65000.50f, "2024-01-01T12:00:00");

        when(binanceProxyService.getCryptoPrice("BTCUSDT")).thenReturn(mockCrypto);

        boolean isInRange = cryptoPriceService.isPriceInRange(exchangeAttempt);

        assertTrue(isInRange);
    }

    @Test
    void test_05_isPriceInRange_ExchangeAttemptDto_outOfRange() {
        ExchangeAttemptDto exchangeAttempt = ExchangeAttemptDto.builder()
                .crypto(CryptoSymbol.BTCUSDT)
                .price(10000.0f) // very different price
                .build();

        CryptoCurrency mockCrypto = new CryptoCurrency("BTCUSDT", 65000.50f, "2024-01-01T12:00:00");

        when(binanceProxyService.getCryptoPrice("BTCUSDT")).thenReturn(mockCrypto);

        boolean isInRange = cryptoPriceService.isPriceInRange(exchangeAttempt);

        assertFalse(isInRange);
    }
}