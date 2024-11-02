package unq.cryptoexchange.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import unq.cryptoexchange.models.CryptoHolding;
import unq.cryptoexchange.models.enums.CryptoSymbol;

@Repository
public interface CryptoHoldingRepository extends JpaRepository<CryptoHolding, Long> {
    
    @Query("SELECT quantity FROM CryptoHolding c WHERE c.cryptoSymbol = :crypto AND c.personId = :personId")
    public int getQuantityCryptoUser(Long personId, CryptoSymbol crypto);

}
