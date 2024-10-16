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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CyptoController.class)
public class CryptoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CryptoPriceService cryptoPriceService;

    @Test
    public void test_01_getCryptoPrice() throws Exception {

        String symbol = "BTCUSDT";
        CryptoCurrency mockCrypto = new CryptoCurrency(symbol, 65000.50f, "2024-01-01T12:00:00");

        when(cryptoPriceService.getPrice(symbol)).thenReturn(mockCrypto);

        mockMvc.perform(get("/api/v1/crypto/price/{symbol}", symbol)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"symbol\":\"BTCUSDT\",\"price\":65000.50,\"lastUpdateDateAndTime\":\"2024-01-01T12:00:00\"}"));
    }


}
