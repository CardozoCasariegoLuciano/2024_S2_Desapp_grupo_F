package unq.cryptoexchange.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolationException;
import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
import unq.cryptoexchange.models.ExchangeAttempt;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.repository.ExchangeAttemptRepository;
import unq.cryptoexchange.repository.PersonRepository;
import unq.cryptoexchange.services.ExAttemptServiceInterface;

@Service
public class ExchangeAttemptService implements ExAttemptServiceInterface {
    
    
    private final PersonRepository personRepository;
    //private final ExchangeAttemptRepository exAttemptRepository;

    @Autowired
    public ExchangeAttemptService(PersonRepository personRepository, ExchangeAttemptRepository exchangeRepository){
        //this.exAttemptRepository = exchangeRepository;
        this.personRepository = personRepository;
    }

    @Override
    public ExchangeAttempt publishExchange(ExchangeAttemptDto ExAtDto) {

        Person person = personRepository.getPersonById(ExAtDto.getPersonId());

        if(person == null){
            throw new ConstraintViolationException("This person with id: " + ExAtDto.getPersonId() + " not already exists", null);
        }

        ExchangeAttempt exAttempt = person.createAttempt(
            ExAtDto.getCrypto(), 
            ExAtDto.getQuantity(), 
            ExAtDto.getPrice(), 
            ExAtDto.getAmountARG(),
            ExAtDto.getOperationType()
        );
        
        personRepository.save(person);

        return exAttempt;
    } 

    @Override
    public void cleanAll() {
        personRepository.deleteAll();
    }
}
