package unq.cryptoexchange.components;

import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import unq.cryptoexchange.models.Person;
import unq.cryptoexchange.models.security.PersonLogin;
import unq.cryptoexchange.repository.PersonRepository;
import unq.cryptoexchange.services.security.JWTService;

@Component
public class JWTFilter extends org.springframework.web.filter.OncePerRequestFilter {

    private final JWTService jwtService;
    private final PersonRepository personRepository;

    public JWTFilter(JWTService jwtService, PersonRepository personRepository)
    {
        this.jwtService = jwtService;
        this.personRepository = personRepository;
    }

    @SuppressWarnings({ "null", "unchecked" })
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, java.io.IOException
    {
        String authHeader = request.getHeader("Authorization");

        System.out.println("Request URI: " + request.getRequestURI());

        if (request.getRequestURI().equals("api/v1/person/registration")||request.getRequestURI().equals("api/v1/person/login")) {
            chain.doFilter(request, response); 
            return;
        }

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                Claims claims = jwtService.validateToken(token);

                String email = claims.getSubject();
                Person person = personRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

                var authentication = new UsernamePasswordAuthenticationToken(
                        new PersonLogin(person), null, new ArrayList());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                System.out.println("Token inválido o expirado: " + e.getMessage());
            }
        }

        chain.doFilter(request, response);
    }
    
}
