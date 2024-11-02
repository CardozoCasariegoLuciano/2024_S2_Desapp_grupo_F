package unq.cryptoexchange.services.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import unq.cryptoexchange.models.enums.CryptoSymbol;
import unq.cryptoexchange.repository.CryptoHoldingRepository;
import unq.cryptoexchange.repository.PersonRepository;
import unq.cryptoexchange.services.CryptoHoldingServiceInterface;

@Service
public class CryptoHoldingService implements CryptoHoldingServiceInterface {
    
    private final PersonRepository personRepository;
    private final CryptoHoldingRepository cryptoRepository;

    @Autowired
    public CryptoHoldingService(PersonRepository personRepository , CryptoHoldingRepository cryptoRepository ) {
        this.personRepository = personRepository;
        this.cryptoRepository = cryptoRepository;
    }

    @Override
    public boolean personHaveThisCant(Long personId, CryptoSymbol crypto, int qty) {
        
        int qtyPerson = cryptoRepository.getQuantityCryptoPerson(personId, crypto);

        return qtyPerson >= qty;
    }    

    @Override
    public void cleanAll() {
        personRepository.deleteAll();
    }

    
}
