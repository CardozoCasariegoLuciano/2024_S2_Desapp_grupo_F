package unq.cryptoexchange.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import unq.cryptoexchange.dto.request.PersonRegistrationDto;
import unq.cryptoexchange.services.impl.PersonService;

@RestController
@RequestMapping("api/v1/person")
public class PersonController {


    PersonService personService;

    @Autowired
    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PostMapping("registration")
    public ResponseEntity<Map<String, String>> registerPerson(@RequestBody PersonRegistrationDto personBody){
        try{
            
            personService.savePerson(personBody);

            Map<String, String> response = Map.of("message","Person registered successfully" );
            return new ResponseEntity(response, HttpStatus.CREATED);

        }
        catch (Exception e){

            Map<String, String> erroResponse = Map.of("message","Error during registration: " + e.getMessage() );
            return new ResponseEntity(erroResponse, HttpStatus.BAD_REQUEST);
        
        }        
    }

    

}