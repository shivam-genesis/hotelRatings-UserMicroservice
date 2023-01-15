package com.microserviceUser.service;

import java.util.List;

import com.microserviceUser.entity.User;

public interface UserService {
	User createUser(User user);

	User getUserById(String userId);

	List<User> getAllUser();

	User updateUserName(String userId, String userName);

	User updateUserEmail(String userId, String userEmail);

	void deleteUser(String userId);
}
