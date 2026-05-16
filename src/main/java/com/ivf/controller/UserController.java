package com.ivf.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ivf.dto.UserDTO;
import com.ivf.services.UserService;


@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping("/find-by-role")
	public ResponseEntity<List<UserDTO>> findByRole(UserDTO request) {

		List<UserDTO> userDTOs = userService.findByRole(request);

		return ResponseEntity.ok(userDTOs);
	}
}
