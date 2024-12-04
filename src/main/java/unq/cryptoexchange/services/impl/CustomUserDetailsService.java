package unq.cryptoexchange.services.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import unq.cryptoexchange.exceptions.NotFoundExceptions;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.repository.PersonRepository;

import java.util.Optional;

@Service
public class CustomUserDetailsService  implements UserDetailsService  {

    private final PersonRepository personRepository;

    public CustomUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Person> opPerson = this.personRepository.findByEmail(email);

        if(opPerson.isEmpty()){
            throw new NotFoundExceptions("No se encontro al usuario con el email " + email);
        }

        return User.builder()
                .username(email)
                .password(opPerson.get().getPassword())
                .roles("USER")
                .build();
    }
}
