package unq.cryptoexchange.PersonTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import unq.cryptoexchange.models.ExchangeAttempt;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.models.enums.AttemptStatus;
import unq.cryptoexchange.models.enums.OperationType;

@SpringBootTest
class PersonModelTest {

    @Test
    void test_01_APersonCanCreateAExchangeAttempt() {
        Person person = new Person();
        ExchangeAttempt attemp = person.createAttempt("test",23,22.4f, OperationType.BUY);

        Assertions.assertEquals("test", attemp.getCrypto());
    }

    @Test
    void test_02_APersonHaveInterestInExchangeAttempt() {
        Person personA = new Person();
        Person personB = new Person();

        ExchangeAttempt attemp = personA.createAttempt("test",23,22.4f, OperationType.BUY);

        Assertions.assertEquals(AttemptStatus.OPEN, attemp.getStatus());
        personB.buyCrypto(attemp);
        Assertions.assertEquals(AttemptStatus.PENDING, attemp.getStatus());
    }
}
