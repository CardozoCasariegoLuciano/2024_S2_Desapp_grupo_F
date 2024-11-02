package unq.cryptoexchange.CryptoHoldingTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import unq.cryptoexchange.models.enums.CryptoSymbol;
import unq.cryptoexchange.repository.CryptoHoldingRepository;
import unq.cryptoexchange.services.impl.CryptoHoldingService;

@SpringBootTest
public class CryptoHoldingServiceTest {
    
    @Mock
    private CryptoHoldingRepository cryptoRepository;

    @InjectMocks
    private CryptoHoldingService cryptoService;

    private Long testPersonId;
    private CryptoSymbol testCrypto;

    @BeforeEach
    void setUp() {

        testPersonId = 1L;
        testCrypto = CryptoSymbol.BTCUSDT;

    }

    @Test
    public void test_01_PersonHaveThisCant() {

        when(cryptoRepository.getQuantityCryptoUser(testPersonId, testCrypto)).thenReturn(10);

        assertTrue(cryptoService.personHaveThisCant(testPersonId, testCrypto, 5));
        assertTrue(cryptoService.personHaveThisCant(testPersonId, testCrypto, 10));

    }

    @Test
    public void test_02_PersonNotHaveThisCant() {

        when(cryptoRepository.getQuantityCryptoUser(testPersonId, testCrypto)).thenReturn(3);

        assertFalse(cryptoService.personHaveThisCant(testPersonId, testCrypto, 5));
    }
}
