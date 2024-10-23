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
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public Person savePerson(PersonRegistrationDto personDto) {

        Optional<Person> existPerson = this.personRepository.findByEmail(personDto.getEmail());
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
    public int personReputation(Long personId){
        
        Optional<Person> existPerson = this.personRepository.findById(personId);
        if(!existPerson.isPresent()){
           throw new InvalidException("This person with id: "  + personId + "not exist");
        }

        //int op = this.personRepository.countClosedOrCancelledAttemptsByPersonId(personId);
        
        return existPerson.get().getPoints()/100;

    }

    @Override
    public void cleanAll() {
        personRepository.deleteAll();
    }    
}
