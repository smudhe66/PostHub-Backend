package com.sm.blog.service.impl;

import java.security.Key;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl {

//	private String secretKey;

//	public JwtServiceImpl() throws NoSuchAlgorithmException {
//		secretKey = generateSecretKey();
//	}
//
//	private String generateSecretKey() throws NoSuchAlgorithmException {
//		KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
//		SecretKey secretKey = keyGenerator.generateKey();
//		return Base64.getEncoder().encodeToString(secretKey.getEncoded());
//	}

	// generate secret key only once

	private Key secretKey;

	@PostConstruct
	public void init() {
		try {
			secretKey = generateSecretKey();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("Failed to initialize JWT secret key", e);
		}
	}

	private Key generateSecretKey() throws NoSuchAlgorithmException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey;
	}
	
	

	public String generateToken(String userName) {
		System.out.println(secretKey);
		Map<String, Object> claims = new HashMap<>();
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 30)) // Example: token valid for 30
																						// minutes
				.signWith(secretKey, SignatureAlgorithm.HS256).compact();
	}

//	private Key getKey() {
//		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
//		return Keys.hmacShaKeyFor(keyBytes);
//	}

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> ClaimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return ClaimsResolver.apply(claims);
	}

	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
	}

	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

//    public boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
	public boolean isTokenExpired(String token) {
		Date expiration = getExpirationDateFromToken(token);
		return expiration != null && expiration.before(new Date());
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String userName = getUsernameFromToken(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
