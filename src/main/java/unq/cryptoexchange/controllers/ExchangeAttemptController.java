package unq.cryptoexchange.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
import unq.cryptoexchange.services.impl.ExchangeAttemptService;
import unq.cryptoexchange.services.impl.PersonService;

@RestController
@RequestMapping("api/v1/exchangeAttempt")
public class ExchangeAttemptController {
    //private final PersonService personService;
    private final ExchangeAttemptService exAttemptService;

    @Autowired
    public ExchangeAttemptController(PersonService personService, ExchangeAttemptService exchangeAttemptService) {
        //this.personService = personService;
        this.exAttemptService = exchangeAttemptService;
    }

    @PostMapping("publishExchange")
    public ResponseEntity<Map<String, String>> publishExchange(@Validated @RequestBody ExchangeAttemptDto exchangeAttempt){
        exAttemptService.publishExchange(exchangeAttempt);
        Map<String, String> response = Map.of("message", "Exchange Published");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}