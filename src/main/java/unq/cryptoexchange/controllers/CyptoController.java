package unq.cryptoexchange.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import unq.cryptoexchange.models.CryptoCurrency;
import unq.cryptoexchange.services.impl.CryptoPriceService;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/crypto")
public class CyptoController {

    @Autowired
    private CryptoPriceService cryptoPriceService;

    @Operation(
        summary = "Get cryptocurrency price", 
        description = "Retrieve the current price of a specific cryptocurrency by its symbol.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cryptocurrency price retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid cryptocurrency symbol"),
        @ApiResponse(responseCode = "404", description = "Cryptocurrency symbol not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/price/{symbol}")
    public CryptoCurrency getCryptoPrice(@PathVariable String symbol) {
        return cryptoPriceService.getPrice(symbol);
    }

    @Operation(
        summary = "Get all cryptocurrency prices in the system", 
        description = "Returns the list of all cryptocurrencies in the system, with their current price..")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cryptocurrency price retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
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
