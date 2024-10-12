package unq.cryptoexchange.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
import unq.cryptoexchange.services.impl.ExchangeAttemptService;

@RestController
@RequestMapping("api/v1/exchangeAttempt")
public class ExchangeAttemptController {
    
    private final ExchangeAttemptService exchangeAttemptService;
    
    @Autowired
    public ExchangeAttemptController(ExchangeAttemptService exAttemptService) {
        this.exchangeAttemptService = exAttemptService;
    }

    @PostMapping("createExAttempt")
    public ResponseEntity<Map<String, String>> registerPerson(@Validated @RequestBody ExchangeAttemptDto exAttemptBody){
            exchangeAttemptService.saveExchangeAttempt(exAttemptBody);
            Map<String, String> response = Map.of("message","Exchange Attempt created successfully" );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
