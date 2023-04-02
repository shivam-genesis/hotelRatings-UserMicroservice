package com.microserviceUser.service;

import java.util.List;

import com.microserviceUser.dto.UserDTO;

public interface UserService {
	UserDTO createUser(UserDTO userDTO);

	UserDTO getUserById(String userId);

	List<UserDTO> getAllUser();

	UserDTO updateUserName(String userId, String userName);

	UserDTO updateUserEmail(String userId, String userEmail);

	void deleteUser(String userId);
}
