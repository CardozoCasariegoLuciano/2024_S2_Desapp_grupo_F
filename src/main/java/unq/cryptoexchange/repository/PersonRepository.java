package unq.cryptoexchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import unq.cryptoexchange.models.Person;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    public Optional<Person> findByEmail(String email);
    //public int getReputationById(Long id); 

    @Query("SELECT COUNT(p.ID) FROM PERSONS p LEFT JOIN EXCHANGE_ATTEMPTS e ON p.ID = e.PERSON_ID WHERE p.ID = :personId AND e.STATUS = 'CLOSE')")
    int countClosedAttemptsByPersonId(Long personId);

    @Query("SELECT COUNT(p.ID) FROM PERSONS p LEFT JOIN EXCHANGE_ATTEMPTS e ON p.ID = e.PERSON_ID WHERE p.id = :personId AND e.status IN ('CLOSE', 'CANCELLED')")
    int countClosedOrCancelledAttemptsByPersonId(Long personId);
}