package unq.CryptoExchange.services;

import unq.CryptoExchange.models.Example;

import java.util.List;

public interface ExampleService {
    List<Example> getAllExamples();
    Example saveExample(Example example);
}
