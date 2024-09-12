package unq.CryptoExchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unq.CryptoExchange.models.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    public Optional<Person> findByEmail(String email);
}