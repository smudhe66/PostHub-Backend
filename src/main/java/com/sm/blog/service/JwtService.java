package com.sm.blog.service;

import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.sm.blog.security.CustomUserDetailService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public interface JwtService {

	public String getUsernameFromToken(String token);
	
	public Date getExpirationDateFromToken(String token);
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> ClaimsResolver);
	
	public Claims getAllClaimsFromToken(String token);
	
	public boolean isTokenExpired(String token);
	
	public boolean validateToken(String token, UserDetailsService userDetailService);
	
	
}
