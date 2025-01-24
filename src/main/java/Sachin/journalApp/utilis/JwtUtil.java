package Sachin.journalApp.utilis;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";//random secret key (always > 32bytes)

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes()); // converting it to a key through HMACSHA256 algorithm to put that in create token method
    }

    public String extractUsername(String token) {// This method is used in JwtFilter class
        Claims claims = extractAllClaims(token);
        return claims.getSubject(); // user nikalo
    }

    public Date extractExpiration(String token) {  // to extract expiration
        return extractAllClaims(token).getExpiration();
    }

    private Claims extractAllClaims(String token) { // extracting all claims
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {  // payload information
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username); // here claims means if you want to send any information in payload you send to it through claims
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject) //username
                .header().empty().add("typ","JWT")// setting header type
                .and()
                .issuedAt(new Date(System.currentTimeMillis())) // time at which token is generated
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 60 minutes expiration time
                .signWith(getSigningKey()) // taking a secret key
                .compact();
    }

    public Boolean validateToken(String token) { // This method is called in jwtFilter class
        return !isTokenExpired(token);
    }


}