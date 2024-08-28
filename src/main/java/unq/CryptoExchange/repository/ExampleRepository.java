package unq.CryptoExchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unq.CryptoExchange.models.Example;

import java.util.Optional;

@Repository
public interface ExampleRepository extends JpaRepository<Example, Long> {
    public Optional<Example> findByName(String name);
}
