package unq.cryptoexchange.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolationException;
import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
import unq.cryptoexchange.dto.request.PersonRegistrationDto;
import unq.cryptoexchange.exceptions.DuplicatedException;
import unq.cryptoexchange.models.ExchangeAttempt;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.repository.PersonRepository;
import unq.cryptoexchange.services.PersonServiceInterface;

import java.util.Optional;

@Service
public class PersonService implements PersonServiceInterface {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person savePerson(PersonRegistrationDto personDto) {

        Optional<Person> existPerson = personRepository.findByEmail(personDto.getEmail());
        if(existPerson.isPresent()){
           throw new DuplicatedException("The email already exists: " + personDto.getEmail());
        }

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

    @Override
    public void publishExchange(ExchangeAttemptDto ExAtDto) {

        boolean existPerson = personRepository.existsById(ExAtDto.getPersonId());
        if(!existPerson){
            throw new ConstraintViolationException("This person " + ExAtDto.getPersonId() + " not already exists", null);
        }

        Person person = personRepository.getPersonById(ExAtDto.getPersonId());

        person.createAttempt(
            ExAtDto.getCrypto(), 
            ExAtDto.getQuantity(), 
            ExAtDto.getPrice(), 
            ExAtDto.getOperationType()
        );

        return personRepository.
    } 

    @Override
    public void cleanAll() {
        personRepository.deleteAll();
    }
}
