package com.microserviceUser.external;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.microserviceUser.entity.Hotel;

@FeignClient(name="HOTEL-SERVICE")
public interface HotelService {
	
	@PostMapping("/hotel")
	Hotel createHotel(@RequestBody Hotel hotel);
	
	@GetMapping("/hotel/{hotelId}")
	Hotel getHotelById(@PathVariable("hotelId") String hotelId);
	
	@GetMapping("/hotels")
	List<Hotel> getHotels();
	
	@DeleteMapping("/hotel/{hotelId}")
	void deleteUser(@PathVariable String hotelId);
}