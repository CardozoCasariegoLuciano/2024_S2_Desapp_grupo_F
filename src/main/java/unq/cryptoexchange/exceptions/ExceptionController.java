package unq.cryptoexchange.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class ExceptionController {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<HashMap<String, String>> invalidDTOArguments(MethodArgumentNotValidException ex){
        HashMap<String, String> resp = new HashMap<>();
        ex.getFieldErrors().forEach(er -> resp.put(er.getField(), er.getDefaultMessage()));

        return new ResponseEntity(resp, HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ExceptionHandler(DuplicatedException.class)
    public ResponseEntity<HashMap<String, String>> duplicatedException(DuplicatedException ex){
        HashMap<String, String> resp = new HashMap<>();
        resp.put("Message", ex.getMessage());
        return new ResponseEntity(resp, HttpStatus.BAD_REQUEST);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ExceptionHandler(InvalidQuantityException.class)
    public ResponseEntity<HashMap<String, String>> invalidQuantity(InvalidQuantityException ex){
        HashMap<String, String> resp = new HashMap<>();
        resp.put("message", ex.getMessage());
        return new ResponseEntity(resp, HttpStatus.BAD_REQUEST);
    }
}
