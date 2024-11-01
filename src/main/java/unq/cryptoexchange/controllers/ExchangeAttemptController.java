package unq.cryptoexchange.controllers;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
import unq.cryptoexchange.dto.request.ItemExAttemptDto;
import unq.cryptoexchange.services.impl.ExchangeAttemptService;

@RestController
@RequestMapping("api/v1/exchangeAttempt")
public class ExchangeAttemptController {
    
    private final ExchangeAttemptService exchangeAttemptService;
    
    @Autowired
    public ExchangeAttemptController(ExchangeAttemptService exAttemptService) {
        this.exchangeAttemptService = exAttemptService;
    }

    @Operation(
        summary = "Register a new ExchangeAttempt",
        description = "This endpoint registers a new ExchangeAttempt in the system by providing the user's information."
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Exchange Attempt created successfully"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data provided"
        )
    })
    @PostMapping("createExAttempt")
    public ResponseEntity<Map<String, String>> createExAttempt(@Validated @RequestBody ExchangeAttemptDto exAttemptBody){
            exchangeAttemptService.saveExchangeAttempt(exAttemptBody);
            Map<String, String> response = Map.of("message","Exchange Attempt created successfully" );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Get all ExchangeAttempt in the system",
        description = "This endpoint returns all ExchangeAttempts on the system. It doesn't matter your status."
    )
     @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Exchange attempts return successfully"),
        @ApiResponse(responseCode = "204", description = "No exchange attempts found"),
        @ApiResponse(responseCode = "500", description = "Internal server error - Please try again later")
    })
    @GetMapping("getAllExAttempt")
    public List<ItemExAttemptDto> getAllExAttempt(){
        return exchangeAttemptService.getAllExchangeAttempt();  
    }
}
