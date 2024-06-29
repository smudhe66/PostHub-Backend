package com.sm.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sm.blog.entities.User;
import com.sm.blog.exceptions.ResourceNotFoundException;
import com.sm.blog.repos.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		
		//loading user from database by username
		
		User user = userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User", "email", username));
		System.out.println(user);
		return user;
	}

}
