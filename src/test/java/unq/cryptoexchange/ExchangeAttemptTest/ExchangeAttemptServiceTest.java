package unq.cryptoexchange.ExchangeAttemptTest;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
import unq.cryptoexchange.dto.request.PersonRegistrationDto;
import unq.cryptoexchange.models.ExchangeAttempt;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.models.enums.CryptoCurrency;
import unq.cryptoexchange.models.enums.OperationType;
import unq.cryptoexchange.services.impl.ExchangeAttemptService;
import unq.cryptoexchange.services.impl.PersonService;

@SpringBootTest
class ExchangeAttemptServiceTest {

    @Autowired
    ExchangeAttemptService exAttemptService;
    @Autowired
    PersonService personService;

    Person person;
    
    @BeforeEach
    void setUp(){
        PersonRegistrationDto personDto =  PersonRegistrationDto.builder()
            .name("Test")
            .lastname("LastTest")
            .email("test.test@example.com")
            .address("Test 123")
            .password("unaPassword123!")
            .cvu("2231456789954442334123")
            .wallet("123456789")
            .build();
        
        person = personService.savePerson(personDto);
    }

    @Test
    void test_01_SuccesfullySaveExAttempt(){

        ExchangeAttemptDto exAttemptDto = ExchangeAttemptDto.builder()
            .personId(person.getId())
            .name(person.getName())
            .lastName(person.getLastname())
            .crypto(CryptoCurrency.BNBUSDT)
            .quantity(1000)
            .price(100.0f)
            .operationType(OperationType.SELL)
            .build();
    
        ExchangeAttempt exAttempt = exAttemptService.saveExchangeAttempt(exAttemptDto);

        Assertions.assertNotEquals(null, exAttempt.getAttemptId());  
    
    }

}
