package unq.cryptoexchange.ExchangeAttemptTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
import unq.cryptoexchange.dto.request.PersonRegistrationDto;
import unq.cryptoexchange.exceptions.InvalidException;
import unq.cryptoexchange.exceptions.NotFoundExceptions;
import unq.cryptoexchange.models.CryptoCurrency;
import unq.cryptoexchange.models.ExchangeAttempt;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.models.enums.AttemptStatus;
import unq.cryptoexchange.models.enums.CryptoSymbol;
import unq.cryptoexchange.models.enums.OperationType;
import unq.cryptoexchange.services.PersonServiceInterface;
import unq.cryptoexchange.services.impl.CryptoPriceService;
import unq.cryptoexchange.services.impl.ExchangeAttemptService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ExchangeAttempsStatusTests {
    @Autowired
    PersonServiceInterface personService;

    @MockBean
    CryptoPriceService cryptoPriceService;

    @Autowired
    ExchangeAttemptService exchangeAttemptService;

    Person creator;
    Person requested;
    ExchangeAttemptDto exchange;

    @BeforeEach
    void setUp(){
        PersonRegistrationDto creatorDto = PersonRegistrationDto.builder()
                .name("Test")
                .lastname("LastTest")
                .email("test.test@example.com")
                .address("Test 123")
                .password("unaPassword123!")
                .cvu("2231456789954442334123")
                .wallet("123456789")
                .build();
        creator = personService.savePerson(creatorDto);

        PersonRegistrationDto requestedDtp = PersonRegistrationDto.builder()
                .name("Test")
                .lastname("LastTest")
                .email("test.es2@example.com")
                .address("Test 123")
                .password("unaPassword123!")
                .cvu("2231456789954442334123")
                .wallet("123456789")
                .build();
        requested = personService.savePerson(requestedDtp);

        when(this.cryptoPriceService.getPrice(anyString())).thenReturn(new CryptoCurrency(CryptoSymbol.BTCUSDT.name(), 2000f, ""));
        Float current = this.cryptoPriceService.getPrice(CryptoSymbol.BTCUSDT.name()).getPrice();
        exchange = ExchangeAttemptDto.builder()
                .personId(creator.getId())
                .name("Test")
                .lastName("LastTest")
                .crypto(CryptoSymbol.BTCUSDT)
                .quantity(20)
                .price(current)
                .operationType(OperationType.BUY)
                .build();
    }

    @Test
    void test_1_AcceptExchangeAttemp_OK(){
        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttemptDto.class))).thenReturn(true);
        ExchangeAttempt savesExchange = exchangeAttemptService.saveExchangeAttempt(exchange);

        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttempt.class))).thenReturn(true);
        exchangeAttemptService.acceptAttemp(savesExchange.getAttemptId(), requested.getId());

        ExchangeAttempt recoverExchange = exchangeAttemptService.findExchangeAttemp(savesExchange.getAttemptId());

        Assertions.assertEquals(recoverExchange.getStatus(), AttemptStatus.PENDING);
        Assertions.assertEquals(recoverExchange.getRequestingUserID(), requested.getId());
        Assertions.assertNotNull(recoverExchange.getLastUpdate());
    }

    @Test
    void test_2_AcceptExchangeAtterm_invalid_id(){
        Exception exception = Assertions.assertThrows(NotFoundExceptions.class, () -> {
            exchangeAttemptService.acceptAttemp(234L, 33L);
        });
        Assertions.assertTrue(exception.getMessage().contains("No se encontro el usuario o el exchange"));
    }

    @Test
    void test_3_AcceptExchangeAtterm_aceptedBYOwner(){
        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttemptDto.class))).thenReturn(true);
        ExchangeAttempt savesExchange = exchangeAttemptService.saveExchangeAttempt(exchange);

        Exception exception = Assertions.assertThrows(InvalidException.class, () -> {
            exchangeAttemptService.acceptAttemp(savesExchange.getAttemptId(), creator.getId());
        });
        Assertions.assertTrue(exception.getMessage().contains("Peticion invalida"));
    }

    @Test
    void test_4_AcceptExchangeAtterm_aceptedANotOpenExchange(){
        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttemptDto.class))).thenReturn(true);
        ExchangeAttempt savesExchange = exchangeAttemptService.saveExchangeAttempt(exchange);

        exchangeAttemptService.acceptAttemp(savesExchange.getAttemptId(), requested.getId());
        Exception exception = Assertions.assertThrows(InvalidException.class, () -> {
            exchangeAttemptService.acceptAttemp(savesExchange.getAttemptId(), requested.getId());
        });
        Assertions.assertTrue(exception.getMessage().contains("Peticion invalida"));
    }

    @Test
    void test_5_ConfirmExchangeAttemp_OK(){
        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttemptDto.class))).thenReturn(true);
        ExchangeAttempt savesExchange = exchangeAttemptService.saveExchangeAttempt(exchange);

        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttempt.class))).thenReturn(true);
        exchangeAttemptService.acceptAttemp(savesExchange.getAttemptId(), requested.getId());

        exchangeAttemptService.confirmAttemp(savesExchange.getAttemptId(), creator.getId());

        ExchangeAttempt recoverExchange = exchangeAttemptService.findExchangeAttemp(savesExchange.getAttemptId());
        Person recoverCreator = personService.findPerson(creator.getId());
        Person recoverRequested = personService.findPerson(requested.getId());


        Assertions.assertEquals(recoverExchange.getStatus(), AttemptStatus.CLOSE);
        Assertions.assertEquals(recoverCreator.getPoints(), 110);
        Assertions.assertEquals(recoverRequested.getPoints(), 110);
    }

    @Test
    void test_6_ConfirmExchangeAtterm_invalid_id(){
        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttemptDto.class))).thenReturn(true);
        ExchangeAttempt savesExchange = exchangeAttemptService.saveExchangeAttempt(exchange);

        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttempt.class))).thenReturn(true);
        exchangeAttemptService.acceptAttemp(savesExchange.getAttemptId(), requested.getId());

        Exception exception = Assertions.assertThrows(NotFoundExceptions.class, () -> {
            exchangeAttemptService.confirmAttemp(234L,33L);
        });
        Assertions.assertTrue(exception.getMessage().contains("No se encontro el usuario o el exchange"));
    }

    @Test
    void test_7_ConfirmExchangeAtterm_aceptedBYRequested(){
        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttemptDto.class))).thenReturn(true);
        ExchangeAttempt savesExchange = exchangeAttemptService.saveExchangeAttempt(exchange);

        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttempt.class))).thenReturn(true);
        exchangeAttemptService.acceptAttemp(savesExchange.getAttemptId(), requested.getId());

        Exception exception = Assertions.assertThrows(InvalidException.class, () -> {
            exchangeAttemptService.confirmAttemp(savesExchange.getAttemptId(), requested.getId());
        });
        Assertions.assertTrue(exception.getMessage().contains("No puedes confirmar esta exchange si no eres el creador"));
    }

    @Test
    void test_8_ConfirmExchangeAtterm_aceptedANotPending(){
        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttemptDto.class))).thenReturn(true);
        ExchangeAttempt savesExchange = exchangeAttemptService.saveExchangeAttempt(exchange);

        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttempt.class))).thenReturn(true);

        Exception exception = Assertions.assertThrows(InvalidException.class, () -> {
            exchangeAttemptService.confirmAttemp(savesExchange.getAttemptId(), requested.getId());
        });
        Assertions.assertTrue(exception.getMessage().contains("Peticion invalida"));
    }

    @Test
    void test_9_CancelExchangeAttemp_OK(){
        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttemptDto.class))).thenReturn(true);
        ExchangeAttempt savesExchange = exchangeAttemptService.saveExchangeAttempt(exchange);

        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttempt.class))).thenReturn(true);
        exchangeAttemptService.acceptAttemp(savesExchange.getAttemptId(), requested.getId());

        exchangeAttemptService.cancelAttemp(savesExchange.getAttemptId(), creator.getId());

        ExchangeAttempt recoverExchange = exchangeAttemptService.findExchangeAttemp(savesExchange.getAttemptId());
        Person recoverCreator = personService.findPerson(creator.getId());
        Person recoverRequested = personService.findPerson(requested.getId());

        Assertions.assertEquals(recoverExchange.getStatus(), AttemptStatus.CANCELLED);
        Assertions.assertEquals(recoverCreator.getPoints(), 80);
        Assertions.assertEquals(recoverRequested.getPoints(), 100);
    }

    @Test
    void test_10_CancelExchangeAttemp_NoValidID(){
        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttemptDto.class))).thenReturn(true);
        ExchangeAttempt savesExchange = exchangeAttemptService.saveExchangeAttempt(exchange);

        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttempt.class))).thenReturn(true);
        exchangeAttemptService.acceptAttemp(savesExchange.getAttemptId(), requested.getId());

        Exception exception = Assertions.assertThrows(NotFoundExceptions.class, () -> {
            exchangeAttemptService.cancelAttemp(123L,22L);
        });
        Assertions.assertTrue(exception.getMessage().contains("No se encontro el usuario o el exchange"));
    }

    @Test
    void test_11_CancelExchangeAttemp_AlreadyCanceled(){
        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttemptDto.class))).thenReturn(true);
        ExchangeAttempt savesExchange = exchangeAttemptService.saveExchangeAttempt(exchange);

        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttempt.class))).thenReturn(true);
        exchangeAttemptService.acceptAttemp(savesExchange.getAttemptId(), requested.getId());

        exchangeAttemptService.cancelAttemp(savesExchange.getAttemptId(), creator.getId());

        Exception exception = Assertions.assertThrows(InvalidException.class, () -> {
            exchangeAttemptService.cancelAttemp(savesExchange.getAttemptId(), creator.getId());
        });
        Assertions.assertTrue(exception.getMessage().contains("Peticion invalida"));
    }

    @Test
    void test_12_CancelExchangeAttemp_CanceledByOwnerWithNoRequestingUser(){
        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttemptDto.class))).thenReturn(true);
        ExchangeAttempt savesExchange = exchangeAttemptService.saveExchangeAttempt(exchange);

        exchangeAttemptService.cancelAttemp(savesExchange.getAttemptId(), creator.getId());

        ExchangeAttempt recoverExchange = exchangeAttemptService.findExchangeAttemp(savesExchange.getAttemptId());
        Person recoverCreator = personService.findPerson(creator.getId());

        Assertions.assertEquals(recoverExchange.getStatus(), AttemptStatus.CANCELLED);
        Assertions.assertEquals(recoverCreator.getPoints(), 100);
    }

    @Test
    void test_13_CancelExchangeAttemp_NoRelatedUser(){
        PersonRegistrationDto noRelatedUserDto = PersonRegistrationDto.builder()
                .name("Test")
                .lastname("LastTest")
                .email("test.es2@norelated.com")
                .address("Test 123")
                .password("unaPassword123!")
                .cvu("2231456789954442334123")
                .wallet("123456789")
                .build();
        Person noRelatedUser = personService.savePerson(noRelatedUserDto);

        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttemptDto.class))).thenReturn(true);
        ExchangeAttempt savesExchange = exchangeAttemptService.saveExchangeAttempt(exchange);

        when(this.cryptoPriceService.isPriceInRange(any(ExchangeAttempt.class))).thenReturn(true);
        exchangeAttemptService.acceptAttemp(savesExchange.getAttemptId(), requested.getId());

        Exception exception = Assertions.assertThrows(InvalidException.class, () -> {
            exchangeAttemptService.cancelAttemp(savesExchange.getAttemptId(), noRelatedUser.getId());
        });
        Assertions.assertTrue(exception.getMessage().contains("El usuario ingresado no pertenese al exchange"));
    }


    @AfterEach
    void clearAll() {
        this.personService.cleanAll();
        this.exchangeAttemptService.cleanAll();
    }
}
