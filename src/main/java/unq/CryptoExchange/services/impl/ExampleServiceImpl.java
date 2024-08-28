package unq.CryptoExchange.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unq.CryptoExchange.exceptions.DuplicatedException;
import unq.CryptoExchange.exceptions.InvalidQuantityException;
import unq.CryptoExchange.models.Example;
import unq.CryptoExchange.repository.ExampleRepository;
import unq.CryptoExchange.services.ExampleService;

import java.util.List;
import java.util.Optional;

@Service
public class ExampleServiceImpl implements ExampleService {
    @Autowired
    ExampleRepository exampleRepository;

    @Override
    public List<Example> getAllExamples() {
        return this.exampleRepository.findAll();
    }

    @Override
    public Example saveExample(Example example) {
        if( example.getQuantity() < 0){
            throw new InvalidQuantityException("La cantidad tiene que ser mayor a 0");
        }
        Optional<Example> exist = this.exampleRepository.findByName(example.getName());
        if(exist.isPresent()){
           throw new DuplicatedException(String.format("El ejemplo con el nombre %s ya existe", example.getName()));
        }

        return this.exampleRepository.save(example);
    }
}
