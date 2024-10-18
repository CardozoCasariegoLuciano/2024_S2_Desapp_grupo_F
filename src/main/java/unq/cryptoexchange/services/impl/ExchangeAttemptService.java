package unq.cryptoexchange.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
import unq.cryptoexchange.dto.request.ItemExAttemptDto;
import unq.cryptoexchange.exceptions.InvalidException;
import unq.cryptoexchange.models.CryptoCurrency;
import unq.cryptoexchange.models.ExchangeAttempt;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.models.enums.AttemptStatus;
import unq.cryptoexchange.repository.ExchangeAttemptRepository;
import unq.cryptoexchange.repository.PersonRepository;
import unq.cryptoexchange.services.ExchangeAttemptServiceInterface;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeAttemptService implements ExchangeAttemptServiceInterface {
    
    private final ExchangeAttemptRepository exAttemptRepository;
    private final PersonRepository personRepository;
    private final CryptoPriceService cryptoPriceService;

    @Autowired
    public ExchangeAttemptService(PersonRepository personRepository, ExchangeAttemptRepository exAttemptRepository, CryptoPriceService cryptoPriceService) {
        this.personRepository = personRepository;
        this.exAttemptRepository = exAttemptRepository;
        this.cryptoPriceService = cryptoPriceService;
    }

    @Override
    public ExchangeAttempt saveExchangeAttempt(ExchangeAttemptDto exAttemptDto) {

        Optional<Person> existPerson = personRepository.findById(exAttemptDto.getPersonId());
        if(existPerson.isEmpty()){
           throw new NullPointerException("This id: " + exAttemptDto.getPersonId() + " does not exist");
        }

        CryptoCurrency priceCrypto = cryptoPriceService.getPrice(exAttemptDto.getCrypto().toString());
        
        if(!priceInMargin(exAttemptDto.getPrice(), priceCrypto.getPrice())){
            throw new InvalidException("This price: " + exAttemptDto.getPrice() + " is out of range ");
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

    private boolean priceInMargin(Float userPrice, Float cryptoPrice){

        float marginPrice = cryptoPrice*0.05f;

        float maxPrice = cryptoPrice + marginPrice;
        float minPrice = cryptoPrice - marginPrice;

        return userPrice >= minPrice && userPrice <= maxPrice;
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
        }).toList();
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
