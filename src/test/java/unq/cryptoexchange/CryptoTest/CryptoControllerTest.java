package unq.cryptoexchange.CryptoTest;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import unq.cryptoexchange.controllers.CyptoController;
import unq.cryptoexchange.models.CryptoCurrency;
import unq.cryptoexchange.services.impl.CryptoPriceService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CyptoController.class)
class CryptoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CryptoPriceService cryptoPriceService;

    @Test
    void test_01_getCryptoPrice() throws Exception {

        String symbol = "BTCUSDT";
        CryptoCurrency mockCrypto = new CryptoCurrency(symbol, 65000.50f, "2024-01-01T12:00:00");

        when(cryptoPriceService.getPrice(symbol)).thenReturn(mockCrypto);

        mockMvc.perform(get("/api/v1/crypto/price/{symbol}", symbol)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"symbol\":\"BTCUSDT\",\"price\":65000.50,\"lastUpdateDateAndTime\":\"2024-01-01T12:00:00\"}"));
    }

    @Test
    void test_02_getCryptoPriceHistory() throws Exception {
        String symbol = "BTCUSDT";

        List<CryptoCurrency> mockPriceHistory = Arrays.asList(
                new CryptoCurrency(symbol, 95000.0f, "2024-10-13T12:00:00"),
                new CryptoCurrency(symbol, 95200.0f, "2024-10-13T13:00:00")
        );

        when(cryptoPriceService.getLast24HoursPrices(symbol)).thenReturn(mockPriceHistory);

        mockMvc.perform(get("/api/v1/crypto/price/history/{symbol}", symbol)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                        [
                            {"symbol":"BTCUSDT","price":95000.0,"lastUpdateDateAndTime":"2024-10-13T12:00:00"},
                            {"symbol":"BTCUSDT","price":95200.0,"lastUpdateDateAndTime":"2024-10-13T13:00:00"}
                        ]
                        """
                ));
    }


}
