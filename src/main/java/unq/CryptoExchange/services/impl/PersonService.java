package unq.CryptoExchange.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unq.CryptoExchange.exceptions.DuplicatedException;
import unq.CryptoExchange.exceptions.InvalidQuantityException;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService implements PersonServiceInterface {
    
    @Autowired
    PersonRepository personRepository;

    @Override
    public Person savePerson(PersonRegistrationDto personDto) {

        Optional<Person> existPerson = this.personRepository.findByEmail(personDto.getEmail());
        if(exist.isPresent()){
           throw new DuplicatedException(String.format("The email already exists.", example.getEmail()));
        }

        Person person = Person.Builder()
            .name(personDto.getName())
            .lastname(personDto.getLastName())
            .email(personDto.getEmail())
            .password(personDto.getPassword())
            .build();
        
        return personRepository.save(person);        
    }
}
