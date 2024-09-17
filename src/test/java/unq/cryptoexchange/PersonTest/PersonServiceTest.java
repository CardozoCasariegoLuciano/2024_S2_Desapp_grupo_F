package unq.cryptoexchange.PersonTest;

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

        Assertions.assertEquals("The email already exists", exception.getMessage());
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

        Exception exception = Assertions.assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            this.personService.savePerson(personDto);
        });

        Assertions.assertEquals("Email should be valid;", exception.getMessage());
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

        Exception exception = Assertions.assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            this.personService.savePerson(personDto);
        });

        Assertions.assertEquals("The password must have at least 1 lowercase, 1 uppercase, 1 special character and at least 6 characters;", exception.getMessage());

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

        Exception exception = Assertions.assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            this.personService.savePerson(personDto);
        });

        Assertions.assertTrue(exception.getMessage().contains("Name is required;"));

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

        Exception exception = Assertions.assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            this.personService.savePerson(personDto);
        });

        Assertions.assertTrue(exception.getMessage().contains("The name must be between 3 and 30 characters;"));

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

        Exception exception = Assertions.assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            this.personService.savePerson(personDto);
        });

        Assertions.assertTrue(exception.getMessage().contains("Lastname is required;"));
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

        Exception exception = Assertions.assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            this.personService.savePerson(personDto);
        });

        Assertions.assertTrue(exception.getMessage().contains("The lastname must be between 3 and 30 characters;"));

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

        Exception exception = Assertions.assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            this.personService.savePerson(personDto);
        });

        Assertions.assertEquals("CVU should be valid;", exception.getMessage());

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

        Exception exception = Assertions.assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            this.personService.savePerson(personDto);
        });

        Assertions.assertEquals("Wallet should be valid;", exception.getMessage());

    }

    @AfterEach
    void clearAll() {
        this.personService.cleanAll();
    }
}
