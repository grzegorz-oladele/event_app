package pl.grzegorz.eventapp.security;

import io.jsonwebtoken.Claims;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;

public interface JwtUtils {

    String extractUsername(String token);

    Date extractExpiration(String token);

    boolean hasClaim(String token, String claimName);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(UserDetails userDetails);

    String generateToken(UserDetails userDetails, Map<String, Object> claims);

    boolean isTokenValid(String token, UserDetails userDetails);
}
