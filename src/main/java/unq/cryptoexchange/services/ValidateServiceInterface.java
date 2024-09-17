package unq.cryptoexchange.services;

import unq.cryptoexchange.dto.request.PersonRegistrationDto;

public interface ValidateServiceInterface {

    void validatePersonDto(PersonRegistrationDto personDto);
    
}