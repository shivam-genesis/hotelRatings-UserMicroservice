package com.microserviceUser.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.microserviceUser.entity.User;
import com.microserviceUser.exceptions.ApiResponse;
import com.microserviceUser.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User u = this.userService.createUser(user);
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable String userId) {
		User u = this.userService.getUserById(userId);
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}

	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = this.userService.getAllUser();
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@PutMapping("/user/{userId}/username")
	public ResponseEntity<User> updateUserName(@PathVariable String userId, @RequestParam String userName) {
		User u = this.userService.updateUserName(userId, userName);
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}

	@PutMapping("/user/{userId}/useremail")
	public ResponseEntity<User> updateUserEmail(@PathVariable String userId, @RequestParam String userEmail) {
		User u = this.userService.updateUserEmail(userId, userEmail);
		return new ResponseEntity<User>(u, HttpStatus.OK);
	}

	@DeleteMapping("/user/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted!!", true), HttpStatus.OK);
	}

}
