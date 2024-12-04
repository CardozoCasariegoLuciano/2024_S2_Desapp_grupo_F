package unq.cryptoexchange.PersonTest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import unq.cryptoexchange.dto.request.PersonLoginDto;
import unq.cryptoexchange.dto.request.PersonRegistrationDto;
import unq.cryptoexchange.exceptions.NotFoundExceptions;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.services.PersonServiceInterface;

@SpringBootTest

class PersonLoginTest {
    @Autowired
    PersonServiceInterface personService;

    Person savedPerson;
    String email = "test.test@example.com";
    String password = "unaPassword123!";

    @BeforeEach
    void setUp(){
        PersonRegistrationDto personDto = PersonRegistrationDto.builder()
                .name("Test")
                .lastname("LastTest")
                .email(email)
                .address("Test 123")
                .password(password)
                .cvu("2231456789954442334123")
                .wallet("123456789")
                .build();

        savedPerson = personService.savePerson(personDto);
    }

    @Test
    void test_01_EmailNotFound() {
        PersonLoginDto body = new PersonLoginDto("fake email", "asd");
        Exception exception = Assertions.assertThrows(NotFoundExceptions.class, () -> {
            this.personService.loginPerson(body);
        });

        Assertions.assertEquals("Email o clave incorrectos", exception.getMessage());
    }

    @Test
    void test_02_PasswordDontMatch() {
        PersonLoginDto body = new PersonLoginDto(email, "asd");
        Exception exception = Assertions.assertThrows(NotFoundExceptions.class, () -> {
            this.personService.loginPerson(body);
        });

        Assertions.assertEquals("Email o clave incorrectos", exception.getMessage());
    }

    @Test
    void test_03_SuccessLogin() {
        PersonLoginDto body = new PersonLoginDto(email, password);
        String token = this.personService.loginPerson(body);

        Assertions.assertNotNull(token);
    }

    @AfterEach
    void clearAll() {
        this.personService.cleanAll();
    }
}
