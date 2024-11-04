package unq.cryptoexchange.PersonTest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import unq.cryptoexchange.models.Exchange;
import unq.cryptoexchange.models.ExchangeAttempt;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.models.enums.AttemptStatus;
import unq.cryptoexchange.models.enums.OperationType;
import unq.cryptoexchange.models.enums.CryptoSymbol;

@SpringBootTest
class PersonModelTest {

    @Test
    void test_01_APersonCanCreateAExchangeAttempt() {
        Person person = new Person();
        ExchangeAttempt attempt = person.createAttempt(CryptoSymbol.BNBUSDT,23,22.4f, OperationType.BUY);

        Assertions.assertEquals(CryptoSymbol.BNBUSDT, attempt.getCrypto());
        Assertions.assertEquals(AttemptStatus.OPEN, attempt.getStatus());
        Assertions.assertEquals(person.getId(), attempt.getPersonId());
        Assertions.assertEquals(515.2f, attempt.getAmountArg());
    }

    @Test
    void test_02_APersonHaveInterestInExchangeAttempt() {
        Person personA = new Person();
        Person personB = new Person();

        ExchangeAttempt attempt = personA.createAttempt(CryptoSymbol.BNBUSDT,23,22.4f, OperationType.BUY);

        Assertions.assertEquals(AttemptStatus.OPEN, attempt.getStatus());
        Exchange exchange = new Exchange(attempt, personB);
        exchange.makeTransfer();
        Assertions.assertEquals(AttemptStatus.PENDING, attempt.getStatus());
    }

    @Test
    void test_03_PersonGetReputationWithoutOp() {
        
        Person personA = new Person();
        
        String rep = personA.getReputation(0);

        Assertions.assertEquals("Sin Operaciones", rep);
    }

    @Test
    void test_03_PersonGetReputationWithOp() {
        
        Person personA = new Person();
        
        String rep = personA.getReputation(10);

        Assertions.assertEquals("10", rep);
    }
}
