package com.microserviceUser.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
	private String ratingId;
	private String hotelId;
	private String rating;
	private String feedback;
	private Hotel hotel;
}
