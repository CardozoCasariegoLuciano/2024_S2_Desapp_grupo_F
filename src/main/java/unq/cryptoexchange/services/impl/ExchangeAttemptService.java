package unq.cryptoexchange.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
import unq.cryptoexchange.models.ExchangeAttempt;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.repository.ExchangeAttemptRepository;
import unq.cryptoexchange.repository.PersonRepository;
import unq.cryptoexchange.services.ExchangeAttemptServiceInterface;

import java.util.Optional;

@Service
public class ExchangeAttemptService implements ExchangeAttemptServiceInterface {
    
    private final ExchangeAttemptRepository exAttemptRepository;
    private final PersonRepository personRepository;

    @Autowired
    public ExchangeAttemptService(PersonRepository personRepository, ExchangeAttemptRepository exAttemptRepository) {
        this.personRepository = personRepository;
        this.exAttemptRepository = exAttemptRepository;
    }

    @Override
    public ExchangeAttempt saveExchangeAttempt(ExchangeAttemptDto exAttemptDto) {

        Optional<Person> existPerson = personRepository.findById(exAttemptDto.getPersonId());
        if(existPerson.isPresent()){
           throw new NullPointerException("This id = " + exAttemptDto.getPersonId() + " does not exist");
        }

        Person person = existPerson.get();

        ExchangeAttempt exAttempt = person.createAttempt(
            exAttemptDto.getCrypto(),
            exAttemptDto.getQuantity(),
            exAttemptDto.getPrice(),
            exAttemptDto.getOperationType()
        );

        return exAttemptRepository.save(exAttempt);
        
    } 

    @Override
    public void cleanAll() {
        exAttemptRepository.deleteAll();
    }

   
}
