package unq.cryptoexchange.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import unq.cryptoexchange.dto.request.PersonRegistrationDto;
import unq.cryptoexchange.dto.response.UserOperations;
import unq.cryptoexchange.services.impl.PersonService;

@RestController
@RequestMapping("api/v1/person")
@Tag(name = "User Registration", description = "Endpoints for user registration and management")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @Operation(summary = "Register a new user", description = "Registers a new user in the system by providing user details, including name, email, and address. "
            + "The email must be unique. Validation is applied to ensure correct data formatting.")
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "201", 
                description = "Person registered successfully",
                content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\": \"Person registered successfully\"}"))),
            @ApiResponse(
                responseCode = "400", 
                description = "Invalid input data provided. Please ensure all fields meet the required validation rules.", 
                content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": \"Invalid data\"}"))),
            @ApiResponse(
                responseCode = "409",
                description = "Conflict - A person with the provided email already exists in the system.",
                content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": \"Person already exists with the provided email\"}")))
    })
    @PostMapping("registration")
    public ResponseEntity<Map<String, String>> registerPerson(
            @Parameter(description = "Details of the person to be registered", required = true) @Validated @RequestBody PersonRegistrationDto personBody) {

        personService.savePerson(personBody);
        Map<String, String> response = Map.of("message", "Person registered successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "User operations between tho dates")
    @PostMapping("/operations/{personID}")
    public ResponseEntity<?> userOperations(@PathVariable Long personID, @RequestParam String init_date, @RequestParam String end_date) {
        UserOperations operations = personService.getUserOperations(personID,init_date,end_date);
        return new ResponseEntity<>(operations, HttpStatus.OK);
    }

}