package unq.cryptoexchange.services;

import unq.cryptoexchange.dto.request.PersonRegistrationDto;
import unq.cryptoexchange.models.Person;

public interface PersonServiceInterface {

    Person savePerson(PersonRegistrationDto personDto);
    void cleanAll();

}