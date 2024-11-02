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
    private final CryptoHoldingService cryptoHoldingService;

    @Autowired
    public ExchangeAttemptService(PersonRepository personRepository, ExchangeAttemptRepository exAttemptRepository,
            CryptoPriceService cryptoPriceService, CryptoHoldingService cryptoHoldingService) {
        this.personRepository = personRepository;
        this.exAttemptRepository = exAttemptRepository;
        this.cryptoPriceService = cryptoPriceService;
        this.cryptoHoldingService = cryptoHoldingService;
    }

    @Override
    public ExchangeAttempt saveExchangeAttempt(ExchangeAttemptDto exAttemptDto) {

        Optional<Person> existPerson = personRepository.findById(exAttemptDto.getPersonId());
        if (existPerson.isEmpty()) {
            throw new NullPointerException("This PersonId: " + exAttemptDto.getPersonId() + " does not exist");
        }

        if(cryptoHoldingService.personHaveThisCant(exAttemptDto.getPersonId(), exAttemptDto.getCrypto(), exAttemptDto.getQuantity())){
            throw new InvalidException("This person with Id: " + exAttemptDto.getPersonId() + " does not have this amount of this crypto available");
        }

        Person person = existPerson.get();

        CryptoCurrency priceCrypto = cryptoPriceService.getPrice(exAttemptDto.getCrypto().toString());
        boolean priceInMargin = priceCrypto.priceInMargin(exAttemptDto.getPrice());
        
        if (!priceInMargin) {
            throw new InvalidException("This price: " + exAttemptDto.getPrice() + " is out of range ");
        }

        ExchangeAttempt exAttempt = person.createAttempt(
                exAttemptDto.getCrypto(),
                exAttemptDto.getQuantity(),
                exAttemptDto.getPrice(),
                exAttemptDto.getOperationType());

        return exAttemptRepository.save(exAttempt);

    }

    @Override
    public List<ItemExAttemptDto> getAllExchangeAttempt() {

        List<ExchangeAttempt> exAttempt = exAttemptRepository.findByStatus(AttemptStatus.OPEN);

        return exAttempt.stream().map(attempt -> {

            Person person = personRepository.findById(attempt.getPersonId()).get();
            int operationsClose = exAttemptRepository.countStatusCloseByPersonId(attempt.getPersonId());
            int operationsFinished = exAttemptRepository.countExchangeAttempByPersonId(attempt.getPersonId());

            return new ItemExAttemptDto(
                    attempt.getCreatedAt(),
                    attempt.getCrypto(),
                    attempt.getCryptoQuantity(),
                    attempt.getPrice(),
                    attempt.getAmountArg(),
                    attempt.getNameUser(),
                    attempt.getLastNameUser(),
                    operationsClose,
                    person.getReputation(operationsFinished));
        }).toList();
    }

    @Override
    public void cleanAll() {
        exAttemptRepository.deleteAll();
    }
}
