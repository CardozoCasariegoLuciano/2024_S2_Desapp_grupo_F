package unq.cryptoexchange.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String, String>> invalidDTOArguments(MethodArgumentNotValidException ex){
        HashMap<String, String> resp = new HashMap<>();
        ex.getFieldErrors().forEach(er -> resp.put(er.getField(), er.getDefaultMessage()));

        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicatedException.class)
    public ResponseEntity<HashMap<String, String>> duplicatedException(DuplicatedException ex){
        HashMap<String, String> resp = new HashMap<>();
        resp.put("Message", ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<HashMap<String, String>> invalidQuantity(InvalidQuantityException ex){
        HashMap<String, String> resp = new HashMap<>();
        resp.put("message", ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<HashMap<String, String>> internalErrorAtSave(ConstraintViolationException ex){
        HashMap<String, String> resp = new HashMap<>();
        resp.put("internal_error", ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundExceptions.class)
    public ResponseEntity<HashMap<String, String>> notFoundExceptions(NotFoundExceptions ex){
        HashMap<String, String> resp = new HashMap<>();
        resp.put("Error", ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExchangeOutOfRange.class)
    public ResponseEntity<HashMap<String, String>> exchangeOutOfRange(ExchangeOutOfRange ex){
        HashMap<String, String> resp = new HashMap<>();
        resp.put("Error", ex.getMessage());
        return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
    }
}
