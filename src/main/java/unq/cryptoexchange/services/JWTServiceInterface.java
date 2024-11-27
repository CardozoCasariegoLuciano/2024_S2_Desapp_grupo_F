package unq.cryptoexchange.services;

import io.jsonwebtoken.Claims;

public interface JWTServiceInterface {

    String generateToken(String email);
    Claims validateToken(String token);

}
