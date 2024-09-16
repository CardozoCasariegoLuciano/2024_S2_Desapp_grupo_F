package unq.CryptoExchange.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unq.CryptoExchange.dto.request.PersonRegistrationDto;
import unq.CryptoExchange.exceptions.DuplicatedException;
import unq.CryptoExchange.models.Person;
import unq.CryptoExchange.repository.PersonRepository;
import unq.CryptoExchange.services.PersonServiceInterface;

import java.util.Optional;

@Service
public class PersonService implements PersonServiceInterface {
    
    @Autowired
    PersonRepository personRepository;

    @Override
    public Person savePerson(PersonRegistrationDto personDto) {

        Optional<Person> existPerson = this.personRepository.findByEmail(personDto.getEmail());
        if(existPerson.isPresent()){
           throw new DuplicatedException(String.format("The email already exists.", personDto.getEmail()));
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
}
