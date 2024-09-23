package unq.cryptoexchange.PersonTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import unq.cryptoexchange.dto.request.PersonRegistrationDto;
import unq.cryptoexchange.models.ExchangeAttempt;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.models.enums.OperationType;

@SpringBootTest
public class PersonModelTest {

    @Test
    void test_01_APersonCanCreateAExchangeAttempt() {
        Person person = new Person();
        ExchangeAttempt attemp = person.createAttempt("test",23,22.4f, OperationType.BUY);

        Assertions.assertEquals(attemp.getCrypto(), "test");

    }
}
