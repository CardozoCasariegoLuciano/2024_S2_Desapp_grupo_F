package unq.CryptoExchange.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import unq.CryptoExchange.dto.request.PersonRegistrationDto;
import unq.CryptoExchange.services.ValidateServiceInterface;

@Service
public class ValidateService implements ValidateServiceInterface {

    @Autowired
    private Validator validator;

    @Override
    public void validatePersonDto(PersonRegistrationDto personDto) {
        Set<ConstraintViolation<PersonRegistrationDto>> violations = validator.validate(personDto);
        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<PersonRegistrationDto> violation : violations) {
                errorMessages.append(violation.getMessage()).append(";");
            }
            throw new ConstraintViolationException(errorMessages.toString(), violations);
        }
    }

    
}
