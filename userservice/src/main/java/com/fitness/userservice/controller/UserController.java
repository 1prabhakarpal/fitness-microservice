package com.fitness.userservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User Service", description = "APIs for user registration and profile retrieval")
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/{emailId}")
	@Operation(summary = "Get User Profile", description = "Retrieve user profile information by user ID")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Successful retrieval of user profile"),
	    @ApiResponse(responseCode = "404", description = "User not found")
	})
	public ResponseEntity<UserResponse> getUserProfile(@PathVariable String emailId) {
		
		try {
			return ResponseEntity.ok(userService.getUserProfile(emailId));
		} catch (RuntimeException e) {
		    return ResponseEntity.notFound().build();
		}
		
	}
	
	@Operation(summary = "Register User", description = "Register a new user with the provided details")
	@ApiResponses(value = {
	    @ApiResponse(responseCode = "200", description = "Successful user registration"),
	    @ApiResponse(responseCode = "400", description = "Invalid input or email already in use")
	})
	@PostMapping("/register")
	public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
		
		return ResponseEntity.ok(userService.registerUser(registerRequest));
	}
	
	@GetMapping("/list")
	public ResponseEntity<List<UserResponse>> getAllUsers() {
	    
	    return ResponseEntity.ok(userService.getAllUsers());
	}
	
	@GetMapping("/{userId}/validate")
	public ResponseEntity<Boolean> validateUser(@PathVariable String userId) {
      
      boolean isValid = userService.validateUser(userId);
      return ResponseEntity.ok(isValid);
  }
}
