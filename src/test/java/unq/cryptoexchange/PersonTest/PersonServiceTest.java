package unq.cryptoexchange.PersonTest;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
import unq.cryptoexchange.dto.request.PersonRegistrationDto;
import unq.cryptoexchange.dto.response.UserOperations;
import unq.cryptoexchange.exceptions.DuplicatedException;
import unq.cryptoexchange.exceptions.InvalidException;
import unq.cryptoexchange.exceptions.NotFoundExceptions;
import unq.cryptoexchange.models.CryptoCurrency;
import unq.cryptoexchange.models.ExchangeAttempt;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.models.enums.CryptoSymbol;
import unq.cryptoexchange.models.enums.OperationType;
import unq.cryptoexchange.services.CryptoPriceServiceInterface;
import unq.cryptoexchange.services.PersonServiceInterface;
import unq.cryptoexchange.services.impl.ExchangeAttemptService;


@SpringBootTest
class PersonServiceTest {

    @Autowired
    PersonServiceInterface personService;
    @Autowired
    ExchangeAttemptService exchangeAttemptService;
    @Autowired
    CryptoPriceServiceInterface cryptoPriceService;

    @Test
    void test_01_SuccessfullySaveAPerson() {
        PersonRegistrationDto personDto = PersonRegistrationDto.builder()
                .name("Test")
                .lastname("LastTest")
                .email("test.test@example.com")
                .address("Test 123")
                .password("unaPassword123!")
                .cvu("2231456789954442334123")
                .wallet("123456789")
                .build();

        Person savedPerson = personService.savePerson(personDto);

        Assertions.assertNotEquals(null, savedPerson.getId());
    }

    @Test
    void test_02_NoSaveAPersonMailTaken() {
        PersonRegistrationDto person1 = PersonRegistrationDto.builder()
                .name("Test")
                .lastname("LastTest")
                .email("test.test@example.com")
                .address("Test 123")
                .password("unaPassword123!")
                .cvu("2231456789954442334123")
                .wallet("123456789")
                .build();

        PersonRegistrationDto person2 = PersonRegistrationDto.builder()
                .name("Test2")
                .lastname("LastTest2")
                .email("test.test@example.com")
                .address("Test 1233")
                .password("unaPassword1233!")
                .cvu("2231456789954442334122")
                .wallet("1234567822")
                .build();

        personService.savePerson(person1);

        Exception exception = Assertions.assertThrows(DuplicatedException.class, () -> {
            this.personService.savePerson(person2);
        });

        Assertions.assertEquals("The email already exists: "+ person2.getEmail(), exception.getMessage());
    }

    @Test
    void test_03_InvalidEmail() {
        PersonRegistrationDto personDto = PersonRegistrationDto.builder()
                .name("Test")
                .lastname("LastTest")
                .email("invalid-email")
                .address("Test 123")
                .password("unaPassword123!")
                .cvu("2231456789954442334123")
                .wallet("123456789")
                .build();

        Exception exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            this.personService.savePerson(personDto);
        });

        Assertions.assertTrue(exception.getMessage().contains("propertyPath=email"));
    }

    @Test
    void test_04_InvalidPassword() {
        PersonRegistrationDto personDto = PersonRegistrationDto.builder()
                .name("Test")
                .lastname("LastTest")
                .email("test.test@example.com")
                .address("Test 123")
                .password("passinvalid")
                .cvu("2231456789954442334123")
                .wallet("123456789")
                .build();

        Exception exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            this.personService.savePerson(personDto);
        });

        Assertions.assertTrue(exception.getMessage().contains("propertyPath=password"));
    }

    @Test
    void test_05_EmptyName() {
        PersonRegistrationDto personDto = PersonRegistrationDto.builder()
                .name(" ")
                .lastname("LastTest")
                .email("test.test@example.com")
                .address("Test 123")
                .password("unaPassword123!")
                .cvu("2231456789954442334123")
                .wallet("123456789")
                .build();

        Exception exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            this.personService.savePerson(personDto);
        });
        Assertions.assertTrue(exception.getMessage().contains("propertyPath=name"));
    }

    @Test
    void test_06_InvalidName() {
        PersonRegistrationDto personDto = PersonRegistrationDto.builder()
                .name("Te")
                .lastname("LastTest")
                .email("test.test@example.com")
                .address("Test 123")
                .password("unaPassword123!")
                .cvu("2231456789954442334123")
                .wallet("123456789")
                .build();

        Exception exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            this.personService.savePerson(personDto);
        });
        Assertions.assertTrue(exception.getMessage().contains("propertyPath=name"));
    }

    @Test
    void test_07_EmptyLastname() {
        PersonRegistrationDto personDto = PersonRegistrationDto.builder()
                .name("Test")
                .lastname(" ")
                .email("test.test@example.com")
                .address("Test 123")
                .password("unaPassword123!")
                .cvu("2231456789954442334123")
                .wallet("123456789")
                .build();

        Exception exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            this.personService.savePerson(personDto);
        });
        Assertions.assertTrue(exception.getMessage().contains("propertyPath=lastname"));
    }

    @Test
    void test_08_InvalidLastname() {
        PersonRegistrationDto personDto = PersonRegistrationDto.builder()
                .name("Test")
                .lastname("La")
                .email("test.test@example.com")
                .address("Test 123")
                .password("unaPassword123!")
                .cvu("2231456789954442334123")
                .wallet("123456789")
                .build();

        Exception exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            this.personService.savePerson(personDto);
        });
        Assertions.assertTrue(exception.getMessage().contains("propertyPath=lastname"));
    }

    @Test
    void test_09_InvalidCVU() {
        PersonRegistrationDto personDto = PersonRegistrationDto.builder()
                .name("Test")
                .lastname("LastTest")
                .email("test.test@example.com")
                .address("Test 123")
                .password("unaPassword123!")
                .cvu("123")
                .wallet("123456789")
                .build();

        Exception exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            this.personService.savePerson(personDto);
        });
        Assertions.assertTrue(exception.getMessage().contains("propertyPath=cvu"));
    }

    @Test
    void test_10_InvalidWallet() {
        PersonRegistrationDto personDto = PersonRegistrationDto.builder()
                .name("Test")
                .lastname("LastTest")
                .email("test.test@example.com")
                .address("Test 123")
                .password("unaPassword123!")
                .cvu("1234532454673216789765")
                .wallet("1234567")
                .build();

        Exception exception = Assertions.assertThrows(ConstraintViolationException.class, () -> {
            this.personService.savePerson(personDto);
        });
        Assertions.assertTrue(exception.getMessage().contains("propertyPath=wallet"));
    }

    @Test
    void test_11_getPersonReputation() {
        PersonRegistrationDto personDto = PersonRegistrationDto.builder()
                .name("Test")
                .lastname("LastTest")
                .email("test.test@example.com")
                .address("Test 123")
                .password("unaPassword123!")
                .cvu("2231456789954442334123")
                .wallet("123456789")
                .build();

        Person savedPerson = personService.savePerson(personDto);

        Assertions.assertEquals(100, savedPerson.getPoints());
    }

    @Test
    void test_12_getOperations_userNotFOund(){
        Exception exception = Assertions.assertThrows(NotFoundExceptions.class, () -> {
            this.personService.getUserOperations(2322L, "", "");
        });
        Assertions.assertTrue(exception.getMessage().contains("No se encontro a la persona con ID"));
    }

    @Test
    void test_13_getOperations_invalidFormatingDate(){
        PersonRegistrationDto personDto = PersonRegistrationDto.builder()
                .name("Test")
                .lastname("LastTest")
                .email("test.test@example.com")
                .address("Test 123")
                .password("unaPassword123!")
                .cvu("2231456789954442334123")
                .wallet("123456789")
                .build();

        Person savedPerson = personService.savePerson(personDto);

        Exception exception = Assertions.assertThrows(InvalidException.class, () -> {
            this.personService.getUserOperations(savedPerson.getId(), "asdasd", "");
        });
        Assertions.assertTrue(exception.getMessage().contains("las fechas no cumplen el formato dd/MM/yyyy"));
    }

    @Test
    void test_14_getOperations_invalidDates(){
        PersonRegistrationDto personDto = PersonRegistrationDto.builder()
                .name("Test")
                .lastname("LastTest")
                .email("test.test@example.com")
                .address("Test 123")
                .password("unaPassword123!")
                .cvu("2231456789954442334123")
                .wallet("123456789")
                .build();

        Person savedPerson = personService.savePerson(personDto);

        Exception exception = Assertions.assertThrows(InvalidException.class, () -> {
            this.personService.getUserOperations(savedPerson.getId(), "17/10/2024", "17/10/1997");
        });
        Assertions.assertTrue(exception.getMessage().contains("La fecha de inicio tiene que ser posterior a la fecha final"));
    }

    @Test
    void test_15_getOperations_OK_emptyresult(){
        PersonRegistrationDto personDto = PersonRegistrationDto.builder()
                .name("Test")
                .lastname("LastTest")
                .email("test.test@example.com")
                .address("Test 123")
                .password("unaPassword123!")
                .cvu("2231456789954442334123")
                .wallet("123456789")
                .build();

        Person savedPerson = personService.savePerson(personDto);

        UserOperations resp = this.personService.getUserOperations(savedPerson.getId(), "17/10/2017", "17/10/2024");

        Assertions.assertEquals(resp.getOperations().size(), 0);
        Assertions.assertEquals(resp.getUsTotal(), 0);
        Assertions.assertEquals(resp.getArgTotal(), 0);
        Assertions.assertNotNull(resp.getRequestTime());
    }

    @Test
    void test_15_getOperations_OK_withData(){
        PersonRegistrationDto creatorDto = PersonRegistrationDto.builder()
                .name("Test")
                .lastname("LastTest")
                .email("test.test@example.com")
                .address("Test 123")
                .password("unaPassword123!")
                .cvu("2231456789954442334123")
                .wallet("123456789")
                .build();
        Person creator = personService.savePerson(creatorDto);

        PersonRegistrationDto requestedDtp = PersonRegistrationDto.builder()
                .name("Test")
                .lastname("LastTest")
                .email("test.es2@example.com")
                .address("Test 123")
                .password("unaPassword123!")
                .cvu("2231456789954442334123")
                .wallet("123456789")
                .build();
        Person requested = personService.savePerson(requestedDtp);

        Float currentPrice = cryptoPriceService.getPrice(CryptoSymbol.BTCUSDT.name()).getPrice();
        ExchangeAttemptDto exchange = ExchangeAttemptDto.builder()
                .personId(creator.getId())
                .name("Test")
                .lastName("LastTest")
                .crypto(CryptoSymbol.BTCUSDT)
                .quantity(20)
                .price(currentPrice)
                .operationType(OperationType.BUY)
                .build();

        ExchangeAttempt savesExchange = exchangeAttemptService.saveExchangeAttempt(exchange);

        exchangeAttemptService.acceptAttemp(savesExchange.getAttemptId(), requested.getId());
        exchangeAttemptService.confirmAttemp(savesExchange.getAttemptId(), creator.getId());

        UserOperations resp = this.personService.getUserOperations(requested.getId(), "17/10/2020", "17/10/2030");

        Assertions.assertEquals(resp.getOperations().size(), 1);
        Assertions.assertTrue(this.AreTheSameNumber(resp.getUsTotal(), savesExchange.getPrice().doubleValue()));
        Assertions.assertEquals(resp.getArgTotal(), savesExchange.getAmountArg().doubleValue());
    }

    @AfterEach
    void clearAll() {
        this.personService.cleanAll();
    }

    public static boolean AreTheSameNumber(Double num1, Double num2) {
        double difference = Math.abs(num1 - num2);
        double threshold = num1 * 0.01;
        return difference <= threshold;
    }
}
