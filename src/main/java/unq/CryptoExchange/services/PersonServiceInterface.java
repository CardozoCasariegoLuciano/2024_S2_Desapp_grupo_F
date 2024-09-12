package unq.CryptoExchange.services;

import unq.CryptoExchange.dto.request.PersonRegistrationDto;
import unq.CryptoExchange.models.Person;

public interface PersonServiceInterface {

    Person savePerson(PersonRegistrationDto personDto);
    
}