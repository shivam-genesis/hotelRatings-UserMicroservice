package com.microserviceUser.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microserviceUser.entity.Hotel;
import com.microserviceUser.entity.Rating;
import com.microserviceUser.entity.User;
import com.microserviceUser.exceptions.AlreadyExistException;
import com.microserviceUser.exceptions.ResourceNotFoundException;
import com.microserviceUser.exceptions.ValidationException;
import com.microserviceUser.external.HotelService;
import com.microserviceUser.external.RatingService;
import com.microserviceUser.repository.UserRepository;
import com.microserviceUser.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

//	@Autowired
//	private RestTemplate restTemplate;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private HotelService hotelService;

//	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User createUser(User user) throws RuntimeException {
		Optional<User> u = this.userRepo.findById(user.getUserId());
		if (!u.isEmpty()) {
			throw new AlreadyExistException("User", user.getUserId());
		} else if (Integer.parseInt(user.getUserId()) == 0) {
			throw new ValidationException("UserId", "0");
		} else if (user.getUserName().length() < 3) {
			throw new ValidationException("UserName", "2");
		} else if (user.getUserEmail().length() < 8) {
			throw new ValidationException("UserEmail", "7");
		} else if (user.getUserAbout() == null) {
			throw new ValidationException("UserAbout", "0");
		}

		User createdUser = this.userRepo.save(user);
		return createdUser;
	}

	@Override
	public User getUserById(String userId) {
		if (Integer.parseInt(userId) == 0) {
			throw new ValidationException("UserId", "0");
		}
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));

//		Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/userid/" + user.getUserId(),
//				Rating[].class);
//		logger.info("{}", ratingsOfUser);
//		List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();

		List<Rating> ratings = ratingService.getRatingsByUser(userId);

		List<Rating> ratingList = ratings.stream().map(r -> {
//			ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotel/" + r.getHotelId(),
//					Hotel.class);
//			Hotel hotel = forEntity.getBody();
			Hotel hotel = hotelService.getHotelById(r.getHotelId());
			r.setHotel(hotel);
			return r;
		}).collect(Collectors.toList());
		user.setRatings(ratingList);

		return user;
	}

	@Override
	public List<User> getAllUser() {
		List<User> users = this.userRepo.findAll();
		for (User u : users) {
			List<Rating> ratings = ratingService.getRatingsByUser(u.getUserId());
			List<Rating> ratingsWithHotels = ratings.stream().map(r -> {
				Hotel hotel = hotelService.getHotelById(r.getHotelId());
				r.setHotel(hotel);
				return r;
			}).collect(Collectors.toList());
			u.setRatings(ratingsWithHotels);
		}
		if (users.isEmpty()) {
			throw new ResourceNotFoundException("Users");
		}
		return users;
	}

	@Override
	public User updateUserName(String userId, String userName) {
		if (userName.length() < 3) {
			throw new ValidationException("UserName", "2");
		}
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
		user.setUserName(userName);
		return this.userRepo.save(user);
	}

	@Override
	public User updateUserEmail(String userId, String userEmail) {
		if (userEmail.length() < 8) {
			throw new ValidationException("UserEmail", "7");
		}
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
		user.setUserEmail(userEmail);
		return this.userRepo.save(user);
	}

	@Override
	public void deleteUser(String userId) {
		this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
		this.userRepo.deleteById(userId);
	}
}
