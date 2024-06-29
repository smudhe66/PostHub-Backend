package com.sm.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sm.blog.exceptions.ApiException;
import com.sm.blog.payloads.JwtAuthRequest;
import com.sm.blog.payloads.JwtAuthResponse;
import com.sm.blog.payloads.UserDto;
import com.sm.blog.service.UserService;
import com.sm.blog.service.impl.JwtServiceImpl;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/auth/")
@Tag(name = "AuthController",description = "APIs for Authentication.")
public class AuthController {
	
	@Autowired
	private JwtServiceImpl jwtServiceImpl;
	
	@Autowired 
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<String> createToken(@RequestBody JwtAuthRequest req) throws Exception
	{
		System.out.println("In createToken()");
		this.authenticate(req.getUsername(),req.getPassword());
		
//		UserDetails userDetails = this.userDetailsService.loadUserByUsername(req.getUserName());
		
		String token = this.jwtServiceImpl.generateToken(req.getUsername());
		
		JwtAuthResponse resp = new JwtAuthResponse();
		resp.setToken(token);
		return new ResponseEntity<>(token,HttpStatus.OK);
		
	}

	private void authenticate(String userName, String password) throws Exception {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userName, password);
		
		try {
			this.authenticationManager.authenticate(authToken);
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details");
			throw new ApiException("Invalid username or password !!");
		}
		
	}
	
	@PostMapping("/userRegister")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto)
	{
		UserDto registerNewUser = this.userService.registerNewUser(userDto);
		
		return new ResponseEntity<UserDto>(registerNewUser,HttpStatus.CREATED);
	}
	
	@PostMapping("/adminRegister")
	public ResponseEntity<UserDto> registerAdmin(@RequestBody UserDto userDto)
	{
		UserDto registerNewUser = this.userService.registerNewAdmin(userDto);
		
		return new ResponseEntity<UserDto>(registerNewUser,HttpStatus.CREATED);
	}
}
