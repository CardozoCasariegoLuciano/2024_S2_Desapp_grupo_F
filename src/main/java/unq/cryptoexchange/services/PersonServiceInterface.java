package unq.cryptoexchange.services;

import unq.cryptoexchange.dto.request.PersonRegistrationDto;
import unq.cryptoexchange.dto.response.UserOperations;
import unq.cryptoexchange.models.Person;

public interface PersonServiceInterface {

    Person savePerson(PersonRegistrationDto personDto);
    void cleanAll();

    UserOperations getUserOperations(Long personID, String initDate, String endDate);
}