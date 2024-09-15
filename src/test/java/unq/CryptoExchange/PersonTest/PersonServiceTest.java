package unq.CryptoExchange.PersonTest;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import unq.CryptoExchange.dto.request.PersonRegistrationDto;
import unq.CryptoExchange.exceptions.DuplicatedException;
import unq.CryptoExchange.models.Person;
import unq.CryptoExchange.services.PersonServiceInterface;
import org.junit.jupiter.api.function.Executable;

@SpringBootTest
public class PersonServiceTest {

    @Autowired
    PersonServiceInterface personService;

    @Test
    public void test_01_SuccessfullySaveAPerson(){
        PersonRegistrationDto personDto = PersonRegistrationDto.builder()
                .email("test.test@example.com")
                .password("test123")
                .build();

        Person savedPerson = this.personService.savePerson(personDto);

        Assertions.assertNotEquals(null,savedPerson.getId());
    }

    @Test
    public void test_02_NoSaveAPersonMailTaken(){
        PersonRegistrationDto personDto = PersonRegistrationDto.builder()
                .email("test.test@example.com")
                .password("test123")
                .build();

        this.personService.savePerson(personDto);
        Executable ejecutable = () -> {
                this.personService.savePerson(personDto);
        };
        Assertions.assertThrows(DuplicatedException.class, ejecutable);
    }

    @AfterEach
    public void clearAll(){
        this.personService.cleanAll();
    }
}
