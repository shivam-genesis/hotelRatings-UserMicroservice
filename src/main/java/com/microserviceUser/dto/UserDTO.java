package com.microserviceUser.dto;

import java.util.List;

import com.microserviceUser.entity.Rating;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	private String userId;

	private String userName;

	private String userEmail;

	private String userAbout;

	private List<Rating> ratings;

}