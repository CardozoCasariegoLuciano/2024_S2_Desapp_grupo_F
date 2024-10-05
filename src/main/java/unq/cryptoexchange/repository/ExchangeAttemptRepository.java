package unq.cryptoexchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import unq.cryptoexchange.models.ExchangeAttempt;

import java.util.Optional;

@Repository
public interface ExchangeAttemptRepository extends JpaRepository<ExchangeAttempt, Long> {

    public Optional<ExchangeAttempt> findByIdOptional(int id);
    public ExchangeAttempt getExchangeAttemptId(int id);

}