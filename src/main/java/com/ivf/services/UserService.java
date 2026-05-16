package com.ivf.services;

import java.util.List;

import com.ivf.dto.UserDTO;

public interface UserService {

	UserDTO login(String email, String password);
	
	UserDTO changePassword(UserDTO userDTO);
	
	public List<UserDTO> findByRole(UserDTO userDTO);

}
