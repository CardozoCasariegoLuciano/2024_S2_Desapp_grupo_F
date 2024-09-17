package unq.cryptoexchange.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unq.cryptoexchange.dto.request.PersonRegistrationDto;
import unq.cryptoexchange.exceptions.DuplicatedException;
import unq.cryptoexchange.exceptions.InvalidException;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.repository.PersonRepository;
import unq.cryptoexchange.services.PersonServiceInterface;

import java.util.Optional;

@Service
public class PersonService implements PersonServiceInterface {

    PersonRepository personRepository;

    ValidateService validator;

    @Autowired
    public PersonService(PersonRepository personRepository, ValidateService validator){
        this.personRepository = personRepository;
        this.validator = validator;
    }

    @Override
    public Person savePerson(PersonRegistrationDto personDto) {

        validator.validatePersonDto(personDto);

        Optional<Person> existPerson = this.personRepository.findByEmail(personDto.getEmail());
        if(existPerson.isPresent()){
           throw new DuplicatedException(String.format("The email already exists", personDto.getEmail()));
        }

        try{ 
            Person person = Person.builder()
            .name(personDto.getName())
            .lastname(personDto.getLastname())
            .email(personDto.getEmail())
            .address(personDto.getAddress())
            .password(personDto.getPassword())
            .cvu(personDto.getCvu())
            .wallet(personDto.getWallet())
            .build();

            return personRepository.save(person);
        }
        catch(Exception e){
            throw new InvalidException(String.format(e.getMessage()));
        }
                   
    }

    @Override
    public void cleanAll() {
        personRepository.deleteAll();
    }    
}
