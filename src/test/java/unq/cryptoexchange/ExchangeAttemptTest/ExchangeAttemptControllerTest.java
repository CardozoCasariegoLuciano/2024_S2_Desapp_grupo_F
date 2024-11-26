package unq.cryptoexchange.ExchangeAttemptTest;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import unq.cryptoexchange.controllers.ExchangeAttemptController;
import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
import unq.cryptoexchange.dto.request.ItemExAttemptDto;
import unq.cryptoexchange.models.enums.CryptoSymbol;
import unq.cryptoexchange.models.enums.OperationType;
import unq.cryptoexchange.services.impl.ExchangeAttemptService;

import java.time.LocalDateTime;
import java.util.List;

class ExchangeAttemptControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ExchangeAttemptService exchangeAttemptService;

    @InjectMocks
    private ExchangeAttemptController exchangeAttemptController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(exchangeAttemptController).build();
    }

    @Test
    void test001_CreateExAttempt() throws Exception {
        ExchangeAttemptDto exchangeAttemptDto = ExchangeAttemptDto.builder()
                .personId(1L)
                .name("Juan")
                .lastName("Perez")
                .crypto(CryptoSymbol.BTCUSDT)
                .quantity(5)
                .price(20000.0f)
                .operationType(OperationType.BUY)
                .build();

        mockMvc.perform(post("/api/v1/exchangeAttempt/createExAttempt")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(exchangeAttemptDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Exchange Attempt created successfully"));

        verify(exchangeAttemptService, times(1)).saveExchangeAttempt(exchangeAttemptDto);
    }

    @Test
    void testGetAllExAttempt() throws Exception {
        List<ItemExAttemptDto> mockAttempts = List.of(
                ItemExAttemptDto.builder()
                        .itemExAttemptID(1L)
                        .createdAt(LocalDateTime.now())
                        .crypto(CryptoSymbol.BTCUSDT)
                        .cryptoQuantity(3)
                        .price(20000.0f)
                        .amountARG(60000.0f)
                        .userName("Juan")
                        .userLastname("Perez")
                        .userOperationsCount(10)
                        .userReputation("10")
                        .build()
        );

        when(exchangeAttemptService.getAllExchangeAttempt()).thenReturn(mockAttempts);

        mockMvc.perform(get("/api/v1/exchangeAttempt/getAllExAttempt"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].itemExAttemptID").value(1L))
                .andExpect(jsonPath("$[0].crypto").value("BTCUSDT"))
                .andExpect(jsonPath("$[0].price").value(20000.0));

        verify(exchangeAttemptService, times(1)).getAllExchangeAttempt();
    }

    @Test
    void testAcceptAttemp() throws Exception {
        mockMvc.perform(post("/api/v1/exchangeAttempt/1/accept/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Exchange aceptada correctamente"));

        verify(exchangeAttemptService, times(1)).acceptAttemp(1L, 1L);
    }

    @Test
    void testConfirmAttemp() throws Exception {
        mockMvc.perform(post("/api/v1/exchangeAttempt/1/confirm/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Exchange confirmada correctamente"));

        verify(exchangeAttemptService, times(1)).confirmAttemp(1L, 1L);
    }

    @Test
    void testCancelAttemp() throws Exception {
        mockMvc.perform(post("/api/v1/exchangeAttempt/1/cancel/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Exchange confirmada correctamente"));

        verify(exchangeAttemptService, times(1)).cancelAttemp(1L, 1L);
    }
}