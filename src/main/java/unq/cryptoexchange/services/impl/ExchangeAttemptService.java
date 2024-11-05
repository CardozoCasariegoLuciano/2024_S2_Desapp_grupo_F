package unq.cryptoexchange.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
import unq.cryptoexchange.dto.request.ItemExAttemptDto;
import unq.cryptoexchange.exceptions.ExchangeOutOfRange;
import unq.cryptoexchange.exceptions.InvalidException;
import unq.cryptoexchange.exceptions.NotFoundExceptions;
import unq.cryptoexchange.models.CryptoCurrency;
import unq.cryptoexchange.models.ExchangeAttempt;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.models.enums.AttemptStatus;
import unq.cryptoexchange.repository.ExchangeAttemptRepository;
import unq.cryptoexchange.repository.PersonRepository;
import unq.cryptoexchange.services.ExchangeAttemptServiceInterface;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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

        if(!cryptoHoldingService.personHaveThisCant(exAttemptDto.getPersonId(), exAttemptDto.getCrypto(), exAttemptDto.getQuantity())){
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
                    attempt.getAttemptId(),
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

    //TODO testear
    @Override
    public void acceptAttemp(Long attempID, Long userID) {
        Optional<ExchangeAttempt> attemp = this.exAttemptRepository.findById(attempID);
        Optional<Person> person = this.personRepository.findById(userID);

        if(attemp.isEmpty() || person.isEmpty()){
            throw new NotFoundExceptions("No se encontro el usuario o el exchange");
        }

        ExchangeAttempt exchange = attemp.get();

        if(Objects.equals(exchange.getPersonId(), userID) || exchange.getStatus() != AttemptStatus.OPEN ){
            throw new InvalidException("Peticion invalida");
        }
        //TODO validar con el profe

        exchange.setRequestingUserID(userID);
        exchange.setLastUpdate(LocalDateTime.now());
        exchange.setStatus(AttemptStatus.PENDING);
        this.exAttemptRepository.save(exchange);
    }

    //TODO testear
    @Override
    public void confirmAttemp(Long attempID, Long userID) {
        Optional<ExchangeAttempt> attemp = this.exAttemptRepository.findById(attempID);
        Optional<Person> person = this.personRepository.findById(userID);

        if(attemp.isEmpty() || person.isEmpty()){
            throw new NotFoundExceptions("No se encontro el usuario o el exchange");
        }

        ExchangeAttempt exchange = attemp.get();

        if(exchange.getRequestingUserID() == null || exchange.getStatus() != AttemptStatus.PENDING ){
            throw new InvalidException("Peticion invalida");
        }

        if(!Objects.equals(exchange.getPersonId(), userID)){
            throw new InvalidException("No puedes confirmar esta exchange si no eres el creador");
        }

        if(this.isPriceInRange(exchange)){
            exchange.setLastUpdate(LocalDateTime.now());
            exchange.setStatus(AttemptStatus.CANCELLED);
            throw new ExchangeOutOfRange("El valor del cripto activo se alejo mucho del valor esperado");
        }

        this.increaseReputation(exchange.getPersonId(), userID, exchange);

        exchange.setLastUpdate(LocalDateTime.now());
        exchange.setStatus(AttemptStatus.CLOSE);
        this.exAttemptRepository.save(exchange);
    }

    //TODO testear
    @Override
    public void cancelAttemp(Long attempID, Long userID) {
        Optional<ExchangeAttempt> attemp = this.exAttemptRepository.findById(attempID);
        Optional<Person> person = this.personRepository.findById(userID);

        if(attemp.isEmpty() || person.isEmpty()){
            throw new NotFoundExceptions("No se encontro el usuario o el exchange");
        }

        ExchangeAttempt exchange = attemp.get();
        Person requesting = person.get();


        if(exchange.getStatus() == AttemptStatus.CANCELLED || exchange.getStatus() == AttemptStatus.CLOSE ){
            throw new InvalidException("Peticion invalida");
        }

        if(Objects.equals(exchange.getRequestingUserID(), userID) || Objects.equals(exchange.getPersonId(), userID)){
            throw new InvalidException("El usuario ingresado no pertenese al exchange");
        }

        //Si el Exchange esta en OPEN (no tiene persona interesada), el creador de
        //esta la puede cancelar sin perder puntos
        if(exchange.getStatus() != AttemptStatus.OPEN){
            requesting.discountPoints(20);
        }

        exchange.setStatus(AttemptStatus.CANCELLED);
        this.exAttemptRepository.save(exchange);
    }

    private void increaseReputation(Long ownerID, Long requestingUserID, ExchangeAttempt attemp){
        Person owner = this.personRepository.findById(ownerID).get();
        Person requesting = this.personRepository.findById(requestingUserID).get();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime attempTime = attemp.getLastUpdate();
        Duration duration = Duration.between(now, attempTime);

        if (duration.toMinutes() >= 30) {
            owner.increasePoints(5);
            requesting.increasePoints(5);
        } else {
            owner.increasePoints(10);
            requesting.increasePoints(10);
        }
    }

    private boolean isPriceInRange(ExchangeAttempt attemp){
        CryptoCurrency currentCryptoPrice = cryptoPriceService.getPrice(attemp.getCrypto().name());
        Float exchangeValue = attemp.getPrice();

        return currentCryptoPrice.priceInMargin(exchangeValue);
    }
}
