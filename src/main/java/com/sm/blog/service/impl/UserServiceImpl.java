package com.sm.blog.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sm.blog.config.AppConstant;
import com.sm.blog.entities.Post;
import com.sm.blog.entities.Role;
import com.sm.blog.entities.User;
import com.sm.blog.exceptions.ResourceNotFoundException;
import com.sm.blog.payloads.PostDto;
import com.sm.blog.payloads.UserDto;
import com.sm.blog.repos.PostRepo;
import com.sm.blog.repos.RoleRepo;
import com.sm.blog.repos.UserRepo;
import com.sm.blog.service.PostService;
import com.sm.blog.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private UserRoleService userRolesService;

	@Autowired
	private PostRepo postRepo;

	BCryptPasswordEncoder passEncoder = new BCryptPasswordEncoder(12);

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		user.setPassword(passEncoder.encode(user.getPassword()));

		// Fetch the role by ID, or throw ResourceNotFoundException if not found
		Role role = roleRepo.findById(AppConstant.ROLE_USER)
				.orElseThrow(() -> new ResourceNotFoundException("Role", "id", AppConstant.ROLE_USER));

		user.getRoles().add(role);

		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}
	
	@Override
	public UserDto registerNewAdmin(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		user.setPassword(passEncoder.encode(user.getPassword()));

		// Fetch the role by ID, or throw ResourceNotFoundException if not found
		Role role = roleRepo.findById(AppConstant.ROLE_ADMIN)
				.orElseThrow(() -> new ResourceNotFoundException("Role", "id", AppConstant.ROLE_USER));

		user.getRoles().add(role);

		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto createUser(UserDto userDto) {

		User user = this.dtoToUser(userDto);
		user.setPassword(passEncoder.encode(user.getPassword()));

//		Set<Role> roles = userDto.getRoles().stream().map(roleName -> {
//			Role existingRole = roleRepo.findByName(roleName);
//			return existingRole != null ? existingRole : new Role(roleName);
//		}).collect(Collectors.toSet());
//
//		user.setRoles(roles);

		User savedUser = this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

//		Set<Role> roles = userDto.getRoles().stream().map(roleName -> {
//			Role existingRole = roleRepo.findByName(roleName);
//			return existingRole != null ? existingRole : new Role(roleName);
//		}).collect(Collectors.toSet());
//
//		user.setRoles(roles);

		User updatedUser = this.userRepo.save(user);

		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

		UserDto userDto = modelMapper.map(user, UserDto.class);

		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepo.findAll();

		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	@Transactional
	@Override
	public void deleteUser(Integer userId) {
		// Check if user roles exist
		boolean userRolesExist = userRolesService.hasUserRoles(userId);

		// Delete user roles if they exist
		if (userRolesExist) {
			userRolesService.deleteAllUserRolesForUser(userId);
		}

		// Fetch the user
		User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		postRepo.deleteByUserId(userId);

		// Finally, delete the user
		userRepo.delete(user);
	}

	private User dtoToUser(UserDto userDto) {
		User u = modelMapper.map(userDto, User.class);
//		u.setId(user.getId());
//		u.setName(user.getName());
//		u.setEmail(user.getEmail());
//		u.setPassword(user.getPassword());
//		u.setAbout(user.getAbout());

		return u;
	}

	private UserDto userToDto(User user) {
		UserDto userDto = modelMapper.map(user, UserDto.class);
//		user.setId(u.getId());
//		user.setName(u.getName()); 
//		user.setEmail(u.getEmail());
//		user.setPassword(u.getPassword());
//		user.setAbout(u.getAbout());

		return userDto;
	}

}
