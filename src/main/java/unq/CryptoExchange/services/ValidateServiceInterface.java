package unq.CryptoExchange.services;

import unq.CryptoExchange.dto.request.PersonRegistrationDto;

public interface ValidateServiceInterface {

    void validatePersonDto(PersonRegistrationDto personDto);
    
}