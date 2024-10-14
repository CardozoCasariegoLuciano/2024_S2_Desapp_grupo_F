package unq.cryptoexchange.PersonTest;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import unq.cryptoexchange.dto.request.PersonRegistrationDto;
import unq.cryptoexchange.exceptions.DuplicatedException;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.services.PersonServiceInterface;


@SpringBootTest
class PersonServiceTest {

    @Autowired
    PersonServiceInterface personService;

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

    @AfterEach
    void clearAll() {
        this.personService.cleanAll();
    }
}
