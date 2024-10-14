package unq.cryptoexchange.services.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
import unq.cryptoexchange.dto.request.ItemExAttemptDto;
import unq.cryptoexchange.models.ExchangeAttempt;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.models.enums.AttemptStatus;
import unq.cryptoexchange.repository.ExchangeAttemptRepository;
import unq.cryptoexchange.repository.PersonRepository;
import unq.cryptoexchange.services.ExchangeAttemptServiceInterface;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

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
        if(existPerson.isEmpty()){
           throw new NullPointerException("This id = " + exAttemptDto.getPersonId() + " does not exist");
        }

        //TODO: Agregar condicion para margen de precio cuando este terminado el punto 3

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
    public List<ItemExAttemptDto> getAllExchangeAttempt() {
        
        List<ExchangeAttempt> exAttempt = exAttemptRepository.findByStatus(AttemptStatus.OPEN);
    
        return exAttempt.stream().map(attempt -> {
            
            //TODO: Resolver OperationCount
            //int operationsCount = getUserOperationCount(attempt.getPersonId());
            
            return new ItemExAttemptDto(
                    attempt.getCreatedAt(),
                    attempt.getCrypto(),
                    attempt.getCryptoQuantity(),
                    attempt.getPrice(),
                    attempt.getAmountArg(),
                    attempt.getNameUser(),
                    attempt.getLastNameUser()
                    //operationsCount,
                    //getUserReputation(attempt.getPersonId())
            );
        }).collect(Collectors.toList());
    }

    /*
    private String getUserReputation(Long personId) {

        Person person = personRepository.findById(personId).get();

        return person.getReputation();
    }


    private int getUserOperationCount(Long personId) {
        
    }*/


@Override
    public void cleanAll() {
        exAttemptRepository.deleteAll();
    }
}
