package com.sm.blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sm.blog.service.impl.JwtServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtServiceImpl jwtServiceImpl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println("In jwt filter");
		String TokenHeader = request.getHeader("Auth");
		System.out.println(request.getHeader("Auth"));
		System.out.println(request.getHeader("User-Agent"));
		String token = null;
		String userName = null;

		if (TokenHeader != null) {
			token = TokenHeader;
			userName = jwtServiceImpl.getUsernameFromToken(token);
			
		} else {
			System.out.println("JWT token doesn't begin with Bearer");
		}
//		System.out.println(userName);
//		System.out.println(token);
		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
			if (jwtServiceImpl.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
			else{
				System.out.println("Invalid JWT token");
			}
		}
		else {
			System.out.println("username is null or context is not null");
		}
		filterChain.doFilter(request, response);

	}

}
