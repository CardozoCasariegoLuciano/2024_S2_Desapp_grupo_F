package unq.cryptoexchange.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import unq.cryptoexchange.models.ExchangeAttempt;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.models.enums.AttemptStatus;
import unq.cryptoexchange.models.enums.CryptoSymbol;
import unq.cryptoexchange.models.enums.OperationType;
import unq.cryptoexchange.repository.ExchangeAttemptRepository;
import unq.cryptoexchange.repository.PersonRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
public class DataGenerator {

    @Bean
    CommandLineRunner initDatabase(PersonRepository personRepository, ExchangeAttemptRepository exchangeAttemptRepository) {
        return args -> {
            generatePersons().forEach( person -> {
                personRepository.save(person);
            });

            generateExchangeAttempts().forEach(atemp -> {
                exchangeAttemptRepository.save(atemp);
            });


            System.out.println("Datos iniciales insertados en la base de datos.");
        };
    }

    public static List<Person> generatePersons() {
        return Arrays.asList(
                Person.builder()
                        .name("John")
                        .lastname("Doe")
                        .email("john.doe@example.com")
                        .address("1234 Elm Street")
                        .password("Passw0rd!")
                        .reputation(10)
                        .cvu("1234567890123456789012")
                        .wallet("wallet123")
                        .build(),

                Person.builder()
                        .name("Jane")
                        .lastname("Smith")
                        .email("jane.smith@example.com")
                        .address("5678 Oak Avenue")
                        .password("Str0ngPwd!")
                        .reputation(15)
                        .cvu("2345678901234567890123")
                        .wallet("wallet456")
                        .build(),

                Person.builder()
                        .name("Alice")
                        .lastname("Johnson")
                        .email("alice.johnson@example.com")
                        .address("9102 Pine Road")
                        .password("Al1cePwd!")
                        .reputation(8)
                        .cvu("3456789012345678901234")
                        .wallet("wallet789")
                        .build(),

                Person.builder()
                        .name("Bob")
                        .lastname("Williams")
                        .email("bob.williams@example.com")
                        .address("3456 Maple Drive")
                        .password("Bob$ecure1")
                        .reputation(12)
                        .cvu("4567890123456789012345")
                        .wallet("wallet321")
                        .build(),

                Person.builder()
                        .name("Charlie")
                        .lastname("Brown")
                        .email("charlie.brown@example.com")
                        .address("7890 Cedar Lane")
                        .password("CharL!3pw")
                        .reputation(5)
                        .cvu("5678901234567890123456")
                        .wallet("wallet654")
                        .build()
        );
    }

    public static List<ExchangeAttempt> generateExchangeAttempts() {
        return Arrays.asList(
                ExchangeAttempt.builder()
                        .personId(1L)
                        .nameUser("John")
                        .lastNameUser("Doe")
                        .crypto(CryptoSymbol.AAVEUSDT)
                        .cryptoQuantity(2)
                        .price(30000.50f)
                        .amountArg(60000.50f)
                        .createdAt(LocalDateTime.now().minusDays(2))
                        .status(AttemptStatus.OPEN)
                        .operationType(OperationType.BUY)
                        .build(),

                ExchangeAttempt.builder()
                        .personId(2L)
                        .nameUser("Jane")
                        .lastNameUser("Smith")
                        .crypto(CryptoSymbol.AAVEUSDT)
                        .cryptoQuantity(5)
                        .price(2000.75f)
                        .amountArg(10003.75f)
                        .createdAt(LocalDateTime.now().minusHours(5))
                        .status(AttemptStatus.OPEN)
                        .operationType(OperationType.SELL)
                        .build(),

                ExchangeAttempt.builder()
                        .personId(3L)
                        .nameUser("Alice")
                        .lastNameUser("Johnson")
                        .crypto(CryptoSymbol.AAVEUSDT)
                        .cryptoQuantity(1000)
                        .price(1.00f)
                        .amountArg(1000.00f)
                        .createdAt(LocalDateTime.now().minusWeeks(1))
                        .status(AttemptStatus.OPEN)
                        .operationType(OperationType.BUY)
                        .build(),

                ExchangeAttempt.builder()
                        .personId(4L)
                        .nameUser("Bob")
                        .lastNameUser("Williams")
                        .crypto(CryptoSymbol.AAVEUSDT)
                        .cryptoQuantity(10)
                        .price(150.50f)
                        .amountArg(1505.00f)
                        .createdAt(LocalDateTime.now().minusDays(10))
                        .status(AttemptStatus.OPEN)
                        .operationType(OperationType.SELL)
                        .build(),

                ExchangeAttempt.builder()
                        .personId(5L)
                        .nameUser("Charlie")
                        .lastNameUser("Brown")
                        .crypto(CryptoSymbol.AAVEUSDT)
                        .cryptoQuantity(3)
                        .price(300.00f)
                        .amountArg(900.00f)
                        .createdAt(LocalDateTime.now().minusMinutes(30))
                        .status(AttemptStatus.OPEN)
                        .operationType(OperationType.BUY)
                        .build()
        );
    }
}

