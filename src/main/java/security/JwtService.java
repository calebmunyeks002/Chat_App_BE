package com.finchat.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // =====================================================
    // SIGNING KEY
    // =====================================================

    private Key getSigningKey() {

        return Keys.hmacShaKeyFor(
                secret.getBytes()
        );

    }

    // =====================================================
    // GENERATE JWT TOKEN
    // =====================================================

    public String generateToken(String username) {

        return Jwts.builder()

                .setSubject(username)

                .setIssuedAt(new Date())

                .setExpiration(

                        new Date(

                                System.currentTimeMillis()
                                        + expiration

                        )

                )

                .signWith(

                        getSigningKey(),

                        SignatureAlgorithm.HS256

                )

                .compact();

    }

    // =====================================================
    // EXTRACT USERNAME
    // =====================================================

    public String extractUsername(String token) {

        return extractClaim(

                token,

                Claims::getSubject

        );

    }

    // =====================================================
    // EXTRACT EXPIRATION
    // =====================================================

    public Date extractExpiration(String token) {

        return extractClaim(

                token,

                Claims::getExpiration

        );

    }

    // =====================================================
    // CHECK IF TOKEN IS EXPIRED
    // =====================================================

    public boolean isTokenExpired(String token) {

        return extractExpiration(token)

                .before(new Date());

    }

    // =====================================================
    // VALIDATE TOKEN (FOR FILTER)
    // =====================================================

    public boolean isTokenValid(String token) {

        try {

            return !isTokenExpired(token);

        }

        catch (Exception e) {

            return false;

        }

    }

    // =====================================================
    // VALIDATE TOKEN AGAINST USERNAME
    // (Used later with UserDetailsService)
    // =====================================================

    public boolean isTokenValid(

            String token,

            String username

    ) {

        final String extractedUsername =

                extractUsername(token);

        return extractedUsername.equals(username)

                &&

                !isTokenExpired(token);

    }

    // =====================================================
    // EXTRACT ANY CLAIM
    // =====================================================

    public <T> T extractClaim(

            String token,

            Function<Claims, T> claimsResolver

    ) {

        final Claims claims =

                extractAllClaims(token);

        return claimsResolver.apply(claims);

    }

    // =====================================================
    // EXTRACT ALL CLAIMS
    // =====================================================

    private Claims extractAllClaims(String token) {

        return Jwts

                .parserBuilder()

                .setSigningKey(

                        getSigningKey()

                )

                .build()

                .parseClaimsJws(token)

                .getBody();

    }

}