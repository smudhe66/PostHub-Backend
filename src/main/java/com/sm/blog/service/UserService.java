package com.sm.blog.service;

import java.util.List;

import com.sm.blog.payloads.UserDto;

public interface UserService {

	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);
	UserDto registerNewUser(UserDto userDto);
	UserDto registerNewAdmin(UserDto userDto);
}
