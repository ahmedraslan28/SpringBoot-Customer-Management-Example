package com.raslan.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.MacAlgorithm;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class JWTUtil {


    @Value("${JWT_SECRET_KEY}")
    private String SECRET_KEY ;

    private static final MacAlgorithm algorithm = Jwts.SIG.HS256 ;



    public String issueToken(String subject){
        return issueToken(subject, Map.of());
    }

    public String issueToken(String subject, String ...scopes){
        return issueToken(subject, Map.of("scopes", scopes));
    }


    public String issueToken(String subject, Map<String, Object> claims){
        return Jwts
                .builder()
                .claims(claims)
                .subject(subject)
                .issuer("reso.com")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plus(15, DAYS)))
                .signWith(getSigningKey(), algorithm)
                .compact();
    }

    public Claims getClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getSubject(String token){
        return getClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token){
        Date today = Date.from(Instant.now()) ;
        return getClaims(token).getExpiration().before(today);
    }

    public boolean isTokenValid(String token, String username){
        String subject = getSubject(token);
        return subject.equals(username) && !isTokenExpired(token) ;


    }


    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes()) ;
    }
}
