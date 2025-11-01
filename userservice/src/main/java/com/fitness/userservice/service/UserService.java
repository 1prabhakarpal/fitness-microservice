package com.fitness.userservice.service;

import org.springframework.stereotype.Service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repo.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	
	public UserResponse getUserProfile(String userId) {
	    return userRepository.findById(userId)
	            .map(user -> new UserResponse(
	                    user.getId(),
	                    user.getFirstName(),
	                    user.getLastName(),
	                    user.getEmail(),
	                    user.getCreatedAt(),
	                    user.getUpdatedAt()
	            ))
	            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
	}

	public UserResponse registerUser(RegisterRequest registerRequest) {
		
		if(userRepository.existsByEmail(registerRequest.email())) {
			throw new RuntimeException("Email already in use: " + registerRequest.email());
		}
		
		User user = User.builder()
				.firstName(registerRequest.firstName())
				.lastName(registerRequest.lastName())
				.email(registerRequest.email())
				.password(registerRequest.password())
				.build();
		User savedUser = userRepository.save(user);
		
		return new UserResponse(
				savedUser.getId(),
				savedUser.getFirstName(),
				savedUser.getLastName(),
				savedUser.getEmail(),
				savedUser.getCreatedAt(),
				savedUser.getUpdatedAt()
				);
	}

}
