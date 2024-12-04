package unq.cryptoexchange.services.impl;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import unq.cryptoexchange.exceptions.InvalidException;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRETKEY = "yoursecretkeysupermaslargaaaaasd231asdasdwwqeqsda";

    public String extractUsername(String token) {
        try{
            return extractClaim(token, Claims::getSubject);
        }catch (Exception e){
            throw new InvalidException("El token no es valido");
        }
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.secretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateToken(String email) {
        return generateToken(new HashMap<>(), email);
    }

    public String generateToken(Map<String, Object> extraClaims, String userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                .signWith(this.secretKey())
                .compact();
    }

    private SecretKey secretKey(){
        byte[] keybytes = Decoders.BASE64.decode(SECRETKEY);
        return Keys.hmacShaKeyFor(keybytes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
}
