package unq.cryptoexchange.models.security;

import org.springframework.security.core.userdetails.UserDetails;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import unq.cryptoexchange.models.Person;

public class PersonLogin implements UserDetails {
      
        private final Person person;
    
        public PersonLogin(Person person) {
            this.person = person;
        }
    
        public Person getPerson() {
            return person;
        }
    
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return new ArrayList<>(); 
        }
    
        @Override
        public String getPassword() {
            return person.getPassword();
        }
    
        @Override
        public String getUsername() {
            return person.getEmail();
        }
    
        @Override
        public boolean isAccountNonExpired() {
            return true; 
        }
    
        @Override
        public boolean isAccountNonLocked() {
            return true; 
        }
    
        @Override
        public boolean isCredentialsNonExpired() {
            return true; 
        }

        @Override
        public boolean isEnabled() {
            return true; 
        }
    }
