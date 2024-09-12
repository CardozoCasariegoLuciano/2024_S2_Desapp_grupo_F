package unq.CryptoExchange.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import unq.CryptoExchange.dto.request.CreateExampleDto;
import unq.CryptoExchange.models.Example;
import unq.CryptoExchange.services.ExampleService;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("api/v1/example")
public class ExampleController {
    @Autowired
    ExampleService exampleService;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @GetMapping("")
    public ResponseEntity<?> getAllExamples(){
        List<Example> result = this.exampleService.getAllExamples();

        HashMap<String, Object> response = new HashMap<>();
        response.put("data", result);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @PostMapping("")
    public ResponseEntity<?> createExamples(@Validated  @RequestBody CreateExampleDto body){
        Example result = this.exampleService.saveExample(body.toModel());

        HashMap<String, Object> response = new HashMap<>();
        response.put("user_created", result);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
