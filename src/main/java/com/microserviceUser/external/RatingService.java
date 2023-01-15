package com.microserviceUser.external;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.microserviceUser.entity.Rating;

@FeignClient(name="RATING-SERVICE")
public interface RatingService {
	
	@PostMapping("/rating")
	Rating createRating(@RequestBody Rating rating);
		
	@GetMapping("/rating/{ratingId}")
	Rating getRatingById(@PathVariable String ratingId);

	@GetMapping("/ratings")
	List<Rating> getAllRatings();
		
	@GetMapping("/ratings/userid/{userId}")
	List<Rating> getRatingsByUser(@PathVariable String userId);

	@GetMapping("/ratings/hotelid/{hotelId}")
	Rating getRatingsByHotel(@PathVariable String hotelId);

}
