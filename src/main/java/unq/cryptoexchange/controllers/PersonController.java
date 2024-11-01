package unq.cryptoexchange.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import unq.cryptoexchange.dto.request.PersonRegistrationDto;
import unq.cryptoexchange.services.impl.PersonService;

@RestController
@RequestMapping("api/v1/person")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Operation(
        summary = "Register a new user",
        description = "This endpoint registers a new user in the system by providing the user's information. The email must be unique."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Person registered successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data provided"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Person already exists with the provided email"
        )
    })
    @PostMapping("registration")
    public ResponseEntity<Map<String, String>> registerPerson(@Validated @RequestBody PersonRegistrationDto personBody){
            personService.savePerson(personBody);
            Map<String, String> response = Map.of("message","Person registered successfully" );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}