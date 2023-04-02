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

import com.microserviceUser.dto.UserDTO;
import com.microserviceUser.exceptions.ApiResponse;
import com.microserviceUser.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/user")
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
		UserDTO u = this.userService.createUser(userDTO);
		return new ResponseEntity<UserDTO>(u, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable String userId) {
		UserDTO u = this.userService.getUserById(userId);
		return new ResponseEntity<UserDTO>(u, HttpStatus.OK);
	}

	@GetMapping("/users")
	public ResponseEntity<List<UserDTO>> getAllUsers() {
		List<UserDTO> users = this.userService.getAllUser();
		return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
	}

	@PutMapping("/user/{userId}/username")
	public ResponseEntity<UserDTO> updateUserName(@PathVariable String userId, @RequestParam String userName) {
		UserDTO u = this.userService.updateUserName(userId, userName);
		return new ResponseEntity<UserDTO>(u, HttpStatus.OK);
	}

	@PutMapping("/user/{userId}/useremail")
	public ResponseEntity<UserDTO> updateUserEmail(@PathVariable String userId, @RequestParam String userEmail) {
		UserDTO u = this.userService.updateUserEmail(userId, userEmail);
		return new ResponseEntity<UserDTO>(u, HttpStatus.OK);
	}

	@DeleteMapping("/user/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable String userId) {
		this.userService.deleteUser(userId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted!!", true), HttpStatus.OK);
	}

}
