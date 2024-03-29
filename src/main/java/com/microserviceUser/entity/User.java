package com.microserviceUser.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "User_ID")
	private String userId;

	@Column(name = "NAME")
	private String userName;

	@Column(name = "EMAIL")
	private String userEmail;

	@Column(name = "ABOUT")
	private String userAbout;
	
//	@Transient //This is used if we dont want to store this entity into database
//	private List<Rating> ratings;

}