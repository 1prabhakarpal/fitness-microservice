package com.fitness.userservice.service;

import org.springframework.stereotype.Service;

import com.fitness.userservice.dto.UserResponse;
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
	                    user.getFirst_name(),
	                    user.getLast_name(),
	                    user.getEmail(),
	                    user.getCreated_at(),
	                    user.getUpdated_at()
	            ))
	            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
	}


}
