package unq.cryptoexchange.services;

import unq.cryptoexchange.dto.request.PersonRegistrationDto;
import unq.cryptoexchange.dto.response.UserOperations;
import unq.cryptoexchange.models.Person;

public interface PersonServiceInterface {
    
    Person findPerson(Long id);
    Person savePerson(PersonRegistrationDto personDto);
    UserOperations getUserOperations(Long personID, String initDate, String endDate);
    boolean authenticate(String email, String password);
    void cleanAll();

}