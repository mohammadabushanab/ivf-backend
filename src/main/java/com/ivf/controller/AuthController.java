package com.ivf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivf.dto.UserDTO;
import com.ivf.services.UserService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<UserDTO> login(@RequestBody UserDTO request) {

		UserDTO user = userService.login(request.getEmail(), request.getPassword());

		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/change-password")
	public ResponseEntity<UserDTO> changePassword(@RequestBody UserDTO request) {

		UserDTO user = userService.changePassword(request);

		return ResponseEntity.ok(user);
	}
}
