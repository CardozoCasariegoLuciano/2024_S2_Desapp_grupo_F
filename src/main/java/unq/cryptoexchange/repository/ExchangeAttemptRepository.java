package unq.cryptoexchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;

import unq.cryptoexchange.models.ExchangeAttempt;

import java.util.Optional;

@Repository
public interface ExchangeAttemptRepository extends JpaRepository<ExchangeAttempt, Long> {
    public Optional<ExchangeAttempt> findByPersonId(Long id);

    @Query("SELECT exat FROM ExchangeAttempt ea WHERE exat.status = 'OPEN'")
    public ArrayList<ExchangeAttempt> findAllActive();

}