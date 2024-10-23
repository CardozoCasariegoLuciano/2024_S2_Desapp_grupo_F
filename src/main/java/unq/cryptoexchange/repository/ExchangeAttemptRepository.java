package unq.cryptoexchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

import unq.cryptoexchange.models.ExchangeAttempt;
import unq.cryptoexchange.models.enums.AttemptStatus;

import java.util.Optional;

@Repository
public interface ExchangeAttemptRepository extends JpaRepository<ExchangeAttempt, Long> {
    public Optional<ExchangeAttempt> findByPersonId(Long id);
    public List<ExchangeAttempt> findByStatus(AttemptStatus status);
    public int countStatusCloseByPersonId(Long personId);
    @Query("SELECT COUNT(*) FROM ExchangeAttempt e WHERE e.status IN ('CANCELLED','CLOSE')")
    public int countExchangeAttempByPersonId(Long personId);
}