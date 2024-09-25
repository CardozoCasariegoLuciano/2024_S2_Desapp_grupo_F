package unq.cryptoexchange.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import unq.cryptoexchange.dto.request.PersonRegistrationDto;
import unq.cryptoexchange.services.impl.PersonService;

@RestController
@RequestMapping("api/v1/person")
public class PersonController {
    @Autowired
    PersonService personService;

    @PostMapping("registration")
    public ResponseEntity<Map<String, String>> registerPerson(@Validated @RequestBody PersonRegistrationDto personBody){
            personService.savePerson(personBody);
            Map<String, String> response = Map.of("message","Person registered successfully" );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}