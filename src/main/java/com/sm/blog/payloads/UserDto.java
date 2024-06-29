package com.sm.blog.payloads;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sm.blog.entities.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDto {

	private int id;
	
	@Size(min=4, message="Username must be of min 4 characters !!")
	@NotEmpty
	@Schema(name = "user_name",accessMode = AccessMode.READ_ONLY,description = "User name of new user")
	private String name;
	
	@NotEmpty
	@Email(message="Email address is not valid !!")
	private String email;
	
	@NotEmpty()
	@Size(min=3, max=10,message="password must be in between 3 to 10 characters !!")
	private String password;
	
	@NotEmpty
	private String about;
	
//	private Set<String> roles;
	
	private Set<RoleDto> roles = new HashSet<>();
	
}
