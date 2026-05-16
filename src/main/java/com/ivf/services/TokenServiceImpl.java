package com.ivf.services;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenServiceImpl implements TokenService {

	@Value("${token.key}")
	private String key;

	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
	}

	@Override
	public String createToken(String username, String role) {

		Map<String, Object> claims = new HashMap<>();
		claims.put("role", role);

		return Jwts.builder().claims(claims).subject(username).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)).signWith(getSigningKey())
				.compact();
	}

	@Override
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	@Override
	public String extractRole(String token) {
	    return extractClaim(token, claims -> claims.get("role", String.class));
	}
	
	@Override
	public boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}

	private <T> T extractClaim(String token, Function<Claims, T> resolver) {

		Claims claims = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();

		return resolver.apply(claims);
	}
}