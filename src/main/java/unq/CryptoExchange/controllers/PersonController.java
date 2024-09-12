package unq.CryptoExchange.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/person")
public class PersonController {

    @Autowired
    PersonService personService;

    @PostMapping("/registration")
    public ResponseEntity<string> registerPerson(@Validated  @RequestBody PersonRegistrationDto personBody){
        try{
            
            this.personService.savePerson(personBody);
            return new ResponseEntity("Person registered successfully", HttpStatus.CREATED);

        }
        catch (Exception e){

            return new ResponseEntity("Error during registration: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        
        }        
    }
}