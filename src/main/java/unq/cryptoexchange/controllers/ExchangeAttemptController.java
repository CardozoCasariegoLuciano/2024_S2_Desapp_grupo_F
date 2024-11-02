package unq.cryptoexchange.controllers;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import unq.cryptoexchange.dto.request.ExchangeAttemptDto;
import unq.cryptoexchange.dto.request.ItemExAttemptDto;
import unq.cryptoexchange.services.impl.ExchangeAttemptService;

@RestController
@RequestMapping("api/v1/exchangeAttempt")
@Tag(name = "Exchange Attempt Management", description = "Endpoints for managing exchange attempts")
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
            description = "Exchange Attempt created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"message\": \"Exchange Attempt created successfully\"}"))
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid input data provided",
            content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": \"Invalid data\"}"))
        )
    })
    @PostMapping("createExAttempt")
    public ResponseEntity<Map<String, String>> createExAttempt(@Validated @RequestBody ExchangeAttemptDto exAttemptBody){
            exchangeAttemptService.saveExchangeAttempt(exAttemptBody);
            Map<String, String> response = Map.of("message","Exchange Attempt created successfully" );
            return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(
        summary = "Get all ExchangeAttempts",
        description = "Return a list of all ExchangeAttempts in the system with status OPEN."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Exchange attempts retrieved successfully",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = ItemExAttemptDto.class))),
        @ApiResponse(responseCode = "204", description = "No exchange attempts found",
                     content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Internal server error - Please try again later",
                     content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": \"Internal server error\"}")))
    })
    @GetMapping("getAllExAttempt")
    public List<ItemExAttemptDto> getAllExAttempt(){
        return exchangeAttemptService.getAllExchangeAttempt();  
    }
}
