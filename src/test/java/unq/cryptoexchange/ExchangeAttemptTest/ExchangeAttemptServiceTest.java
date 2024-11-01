package unq.cryptoexchange.ExchangeAttemptTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
import unq.cryptoexchange.dto.request.ItemExAttemptDto;
import unq.cryptoexchange.exceptions.InvalidException;
import unq.cryptoexchange.models.CryptoCurrency;
import unq.cryptoexchange.models.ExchangeAttempt;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.models.enums.AttemptStatus;
import unq.cryptoexchange.models.enums.CryptoSymbol;
import unq.cryptoexchange.models.enums.OperationType;
import unq.cryptoexchange.repository.ExchangeAttemptRepository;
import unq.cryptoexchange.repository.PersonRepository;
import unq.cryptoexchange.services.impl.CryptoPriceService;
import unq.cryptoexchange.services.impl.ExchangeAttemptService;

@SpringBootTest
class ExchangeAttemptServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private CryptoPriceService cryptoPriceService;

    @Mock
    private ExchangeAttemptRepository exAttemptRepository;

    @InjectMocks
    private ExchangeAttemptService exchangeAttemptService;
    
    private Person personTest;
    private ExchangeAttemptDto exAttemptTestA;
    private ExchangeAttemptDto exAttemptTestB;
    private ExchangeAttempt attemptTestA;
    private ExchangeAttempt attemptTestB;
    
    @BeforeEach
    void setUp(){

        personTest =  new Person();
        personTest.setName("Test");
        personTest.setLastname("LastTest");
        personTest.setEmail("test.test@example.com");
        personTest.setAddress("Test 123");
        personTest.setPassword("unaPassword123!");
        personTest.setCvu("2231456789954442334123");
        personTest.setWallet("123456789");
    
        exAttemptTestA = new ExchangeAttemptDto();
        exAttemptTestA.setPersonId(personTest.getId());
        exAttemptTestA.setName(personTest.getName());
        exAttemptTestA.setLastName(personTest.getLastname());
        exAttemptTestA.setCrypto(CryptoSymbol.BNBUSDT);
        exAttemptTestA.setQuantity(1000);
        exAttemptTestA.setPrice(579.50f);
        exAttemptTestA.setOperationType(OperationType.SELL);

        exAttemptTestB = new ExchangeAttemptDto();
        exAttemptTestB.setPersonId(personTest.getId());
        exAttemptTestB.setName(personTest.getName());
        exAttemptTestB.setLastName(personTest.getLastname());
        exAttemptTestB.setCrypto(CryptoSymbol.AAVEUSDT);
        exAttemptTestB.setQuantity(500);
        exAttemptTestB.setPrice(500.5f);
        exAttemptTestB.setOperationType(OperationType.SELL);

        attemptTestA = new ExchangeAttempt();
        attemptTestA.setPersonId(personTest.getId());
        attemptTestA.setNameUser(personTest.getName());
        attemptTestA.setLastNameUser(personTest.getLastname());
        attemptTestA.setCrypto(CryptoSymbol.BNBUSDT);
        attemptTestA.setCryptoQuantity(1000);
        attemptTestA.setPrice(579.50f);
        attemptTestA.setOperationType(OperationType.SELL);

        attemptTestB = new ExchangeAttempt();
        attemptTestB.setPersonId(personTest.getId());
        attemptTestB.setNameUser(personTest.getName());
        attemptTestB.setLastNameUser(personTest.getLastname());
        attemptTestB.setCrypto(CryptoSymbol.BNBUSDT);
        attemptTestB.setCryptoQuantity(1000);
        attemptTestB.setPrice(579.50f);
        attemptTestB.setOperationType(OperationType.SELL);

    }

    @Test
    void test_01_SuccesfullySaveExAttempt(){

        when(personRepository.findById(personTest.getId())).thenReturn(Optional.of(personTest));

        CryptoCurrency mockCrypto = new CryptoCurrency("BNBUSDT", 579.50f, null);
        when(cryptoPriceService.getPrice("BNBUSDT")).thenReturn(mockCrypto);
        
        ExchangeAttempt expectedAttempt = new ExchangeAttempt();
        when(exAttemptRepository.save(any(ExchangeAttempt.class))).thenReturn(expectedAttempt);

        ExchangeAttempt result = exchangeAttemptService.saveExchangeAttempt(exAttemptTestA);

        Assertions.assertNotNull(result);
        verify(personRepository).findById(personTest.getId());
        verify(cryptoPriceService).getPrice("BNBUSDT");
        verify(exAttemptRepository).save(any(ExchangeAttempt.class));
        
        Assertions.assertEquals(personTest.getId(), exAttemptTestA.getPersonId());  
        Assertions.assertEquals(CryptoSymbol.BNBUSDT, exAttemptTestA.getCrypto());
    
    }

    @Test
    void test_02_SaveExchangeAttempt_PriceOutOfRange() {
        
        when(personRepository.findById(personTest.getId())).thenReturn(Optional.of(personTest));
        
        CryptoCurrency mockCrypto = new CryptoCurrency("BNBUSDT", 100.0f,null);
        when(cryptoPriceService.getPrice("BNBUSDT")).thenReturn(mockCrypto);

        InvalidException exception = assertThrows(InvalidException.class, () -> {
            exchangeAttemptService.saveExchangeAttempt(exAttemptTestA);
        });

        assertEquals("This price: 579.5 is out of range ", exception.getMessage());
    }

    @Test
    void test_03_GetAllExchangeAtempt_WithoutExchange() {
        
        when(exAttemptRepository.findByStatus(AttemptStatus.OPEN)).thenReturn(Collections.emptyList());

        List<ItemExAttemptDto> result = exchangeAttemptService.getAllExchangeAttempt();

        assertTrue(result.isEmpty());
        verify(exAttemptRepository).findByStatus(AttemptStatus.OPEN);
    }

    @Test
    void test_03_GetAllExchangeAtempt_WithExchangeAttempts() {
        
        when(exAttemptRepository.findByStatus(AttemptStatus.OPEN)).thenReturn(List.of(attemptTestA));

        when(personRepository.findById(personTest.getId())).thenReturn(Optional.of(personTest));
        when(exAttemptRepository.countStatusCloseByPersonId(personTest.getId())).thenReturn(1);
        when(exAttemptRepository.countExchangeAttempByPersonId(personTest.getId())).thenReturn(2);

        List<ItemExAttemptDto> result = exchangeAttemptService.getAllExchangeAttempt();

        assertEquals(2, result.size());
        ItemExAttemptDto dto = result.get(0);
        assertEquals("Test", dto.getUserName());
        assertEquals("LastTest", dto.getUserLastname());
        assertEquals("50", dto.getUserReputation());

        verify(exAttemptRepository).findByStatus(AttemptStatus.OPEN);
        verify(personRepository, atLeastOnce()).findById(personTest.getId());
        verify(exAttemptRepository, atLeastOnce()).countExchangeAttempByPersonId(personTest.getId());
    }

    @Test
    void test_04_GetAllExchangeAtempt_MultipleExchangeAttempts() {
        
        when(exAttemptRepository.findByStatus(AttemptStatus.OPEN)).thenReturn(List.of(attemptTestA, attemptTestB));
        when(personRepository.findById(personTest.getId())).thenReturn(Optional.of(personTest));
        when(exAttemptRepository.countStatusCloseByPersonId(personTest.getId())).thenReturn(1);
        when(exAttemptRepository.countExchangeAttempByPersonId(personTest.getId())).thenReturn(3);

        List<ItemExAttemptDto> result = exchangeAttemptService.getAllExchangeAttempt();

        assertEquals(2, result.size());

        ItemExAttemptDto dto1 = result.get(0);
        ItemExAttemptDto dto2 = result.get(1);

        assertEquals("Test", dto1.getUserName());
        assertEquals("LastTest", dto1.getUserLastname());
        assertEquals("33", dto1.getUserReputation());

        assertEquals("Test", dto2.getUserName());
        assertEquals("LastTest", dto2.getUserLastname());
        assertEquals("33", dto2.getUserReputation());

        verify(exAttemptRepository).findByStatus(AttemptStatus.OPEN);
        verify(personRepository, times(2)).findById(personTest.getId());
        verify(exAttemptRepository, times(2)).countStatusCloseByPersonId(personTest.getId());
        verify(exAttemptRepository, times(2)).countExchangeAttempByPersonId(personTest.getId());
    }
}
