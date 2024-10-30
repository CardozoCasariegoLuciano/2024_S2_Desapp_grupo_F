package unq.cryptoexchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import unq.cryptoexchange.models.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    public Optional<Person> findByEmail(String email);

    @Query("SELECT p.points FROM Person p WHERE p.id = :id")
    public int getPointsById(Long id); 

    @Query("SELECT COUNT(p.id) FROM Person p LEFT JOIN ExchangeAttempt e ON p.id = e.personId WHERE p.id = :personId AND e.status = 'CLOSE'")
    int countClosedAttemptsByPersonId(Long personId);
    
    @Query("SELECT COUNT(p.id) FROM Person p LEFT JOIN ExchangeAttempt e ON p.id = e.personId WHERE p.id = :personId AND e.status IN ('CLOSE', 'CANCELLED')")
    int countClosedOrCancelledAttemptsByPersonId(Long personId);
    
}