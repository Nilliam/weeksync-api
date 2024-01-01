package io.weeksync.weeksync.application.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {

    public static final int TOKEN_DURATION_DAYS = 1;

    public static final long TOKEN_DURATION_MILLIS = Duration.ofDays(TOKEN_DURATION_DAYS).toMillis();

    public static final int TOKEN_DURATION_SECONDS = Long.valueOf(Duration.ofDays(TOKEN_DURATION_DAYS).getSeconds()).intValue();

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String generateToken(String subject) {
        return generateToken(new HashMap<>(), subject);
    }

    public String generateToken(
            HashMap<String, Object> extraClaims,
            String subject
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_DURATION_MILLIS))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return extractAllClaims(token).map(claimsResolver).orElse(null);
    }

    private Optional<Claims> extractAllClaims(String token) {
        try {
            return Optional.of(
                    Jwts
                            .parserBuilder()
                            .setSigningKey(getSignInKey())
                            .build()
                            .parseClaimsJws(token)
                            .getBody()
            );
        } catch (Exception e) {
            log.error("Failed extracting token", e);
            return Optional.empty();
        }
    }

    private Key getSignInKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
}
