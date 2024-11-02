package unq.cryptoexchange.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import unq.cryptoexchange.models.CryptoCurrency;
import unq.cryptoexchange.services.impl.CryptoPriceService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/crypto")
@Tag(name = "Cryptocurrency Price Management", description = "Endpoints for retrieving cryptocurrency prices by symbol or for a list of predefined symbols")
public class CyptoController {

    @Autowired
    private CryptoPriceService cryptoPriceService;

    @Operation(
        summary = "Get cryptocurrency price by symbol", 
        description = "Retrieve the current price of a specific cryptocurrency by providing its symbol. Returns details of the cryptocurrency's current price if found."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cryptocurrency price retrieved successfully",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = CryptoCurrency.class))),
        @ApiResponse(responseCode = "400", description = "Invalid cryptocurrency symbol provided",
                     content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": \"Invalid cryptocurrency symbol\"}"))),
        @ApiResponse(responseCode = "404", description = "Cryptocurrency symbol not found in the system",
                     content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": \"Cryptocurrency not found\"}"))),
        @ApiResponse(responseCode = "500", description = "Internal server error while retrieving cryptocurrency price",
                     content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": \"Internal server error\"}")))
    })
    @GetMapping("/price/{symbol}")
    public CryptoCurrency getCryptoPrice(@PathVariable String symbol) {
        return cryptoPriceService.getPrice(symbol);
    }

    @Operation(
        summary = "Get prices for a predefined list of cryptocurrencies", 
        description = "Retrieve the current prices of a predefined list of popular cryptocurrencies. This endpoint returns price information for each symbol listed in the system."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "List of cryptocurrency prices retrieved successfully",
                     content = @Content(mediaType = "application/json", schema = @Schema(implementation = CryptoCurrency.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error while retrieving cryptocurrency prices",
                     content = @Content(mediaType = "application/json", schema = @Schema(example = "{\"error\": \"Internal server error\"}")))
    })
    @GetMapping("/prices")
    public List<CryptoCurrency> getAllCryptoPrices() {
        List<String> symbols = Arrays.asList(
                "ALICEUSDT", "MATICUSDT", "AXSUSDT", "AAVEUSDT", "ATOMUSDT",
                "NEOUSDT", "DOTUSDT", "ETHUSDT", "CAKEUSDT", "BTCUSDT",
                "BNBUSDT", "ADAUSDT", "TRXUSDT", "AUDIOUSDT");

        return cryptoPriceService.getAllPrices(symbols);
    }
}
