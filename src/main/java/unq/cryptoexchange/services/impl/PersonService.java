package unq.cryptoexchange.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unq.cryptoexchange.dto.request.PersonRegistrationDto;
import unq.cryptoexchange.dto.response.UserOperations;
import unq.cryptoexchange.dto.response.UserSingleOperationDto;
import unq.cryptoexchange.exceptions.DuplicatedException;
import unq.cryptoexchange.exceptions.InvalidException;
import unq.cryptoexchange.exceptions.NotFoundExceptions;
import unq.cryptoexchange.models.CryptoCurrency;
import unq.cryptoexchange.models.ExchangeAttempt;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.models.enums.CryptoSymbol;
import unq.cryptoexchange.repository.ExchangeAttemptRepository;
import unq.cryptoexchange.repository.PersonRepository;
import unq.cryptoexchange.services.CryptoPriceServiceInterface;
import unq.cryptoexchange.services.PersonServiceInterface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService implements PersonServiceInterface {
    private final PersonRepository personRepository;
    private final ExchangeAttemptRepository exchangeAttemptRepository;
    private final CryptoPriceServiceInterface cryptoPriceServiceInterface;


    @Autowired
    public PersonService(PersonRepository personRepository, ExchangeAttemptRepository exchangeAttemptRepository, CryptoPriceServiceInterface cryptoPriceServiceInterface) {
        this.personRepository = personRepository;
        this.exchangeAttemptRepository = exchangeAttemptRepository;
        this.cryptoPriceServiceInterface = cryptoPriceServiceInterface;
    }

    @Override
    public Person savePerson(PersonRegistrationDto personDto) {

        Optional<Person> existPerson = this.personRepository.findByEmail(personDto.getEmail());
        if (existPerson.isPresent()) {
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
    public void cleanAll() {
        personRepository.deleteAll();
    }

    //TODO Agregarle los tests a esto
    @Override
    public UserOperations getUserOperations(Long personID, String initDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Optional<Person> person = this.personRepository.findById(personID);
        if (person.isEmpty()) {
            throw new NotFoundExceptions("No se encontro a la persona con ID" + personID);
        }

        LocalDate init_Date;
        LocalDate end_Date;
        try {
            init_Date = LocalDate.parse(initDate, formatter);
            end_Date = LocalDate.parse(endDate, formatter);

            if (init_Date.isAfter(end_Date)) {
                throw new InvalidException("La fecha de inicio tiene que ser posterior a la fecha final");
            }
        } catch (InvalidException e) {
            throw e;
        } catch (Exception e) {
            throw new InvalidException("las fechas no cumplen el formato dd/MM/yyyy");
        }

        List<ExchangeAttempt> list = this.exchangeAttemptRepository.findExchangesRelatedWidthUser(personID, init_Date, end_Date);

        double[] totals = {0, 0}; //[0] == us_total || [1] == arg_total
        List<UserSingleOperationDto> operations = new java.util.ArrayList<>(List.of());

        list.forEach(attemp -> {
            CryptoCurrency currentCrypto = cryptoPriceServiceInterface.getPrice(attemp.getCrypto().name());
            UserSingleOperationDto userSingleOperationDto = new UserSingleOperationDto(attemp.getCrypto(), attemp.getCryptoQuantity(), currentCrypto.getPrice(), attemp.getAmountArg());
            operations.add(userSingleOperationDto);
            totals[0] += attemp.getPrice();
            totals[1] += attemp.getAmountArg();
        });

        return new UserOperations(totals[0], totals[1], operations);
    }
}
