package com.microserviceUser.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microserviceUser.dto.UserDTO;
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
	private ModelMapper modelMapper;

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
	public UserDTO createUser(UserDTO userDTO) throws RuntimeException {
		User user = this.userDTOToUser(userDTO);
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
		return this.userToUserDTO(createdUser);
	}

	@Override
	public UserDTO getUserById(String userId) {
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
		UserDTO u = this.userToUserDTO(user);
		u.setRatings(ratingList);

		return u;
	}

	@Override
	public List<UserDTO> getAllUser() {
		List<User> users = this.userRepo.findAll();
		List<UserDTO> userDTOS = users.stream().map(user -> this.userToUserDTO(user)).collect(Collectors.toList());
		
		for (UserDTO u : userDTOS) {
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

		return userDTOS;
	}

	@Override
	public UserDTO updateUserName(String userId, String userName) {
		if (userName.length() < 3) {
			throw new ValidationException("UserName", "2");
		}
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
		user.setUserName(userName);
		return this.userToUserDTO(this.userRepo.save(user));
	}

	@Override
	public UserDTO updateUserEmail(String userId, String userEmail) {
		if (userEmail.length() < 8) {
			throw new ValidationException("UserEmail", "7");
		}
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
		user.setUserEmail(userEmail);
		return this.userToUserDTO(this.userRepo.save(user));
	}

	@Override
	public void deleteUser(String userId) {
		this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
		this.userRepo.deleteById(userId);
	}

	public User userDTOToUser(UserDTO userDTO) {
		User user = this.modelMapper.map(userDTO, User.class);
//		user.setId(userDTO.getId());
//		user.setName(userDTO.getName());
//		user.setEmail(userDTO.getEmail());
//		user.setPassword(userDTO.getPassword());
//		user.setAbout(userDTO.getAbout());
		return user;
	}

	public UserDTO userToUserDTO(User user) {
		UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
//		userDTO.setId(user.getId());
//		userDTO.setName(user.getName());
//		userDTO.setEmail(user.getEmail());
//		userDTO.setPassword(user.getPassword());
//		userDTO.setAbout(user.getAbout());
		return userDTO;
	}
}
