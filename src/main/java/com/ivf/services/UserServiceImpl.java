package com.ivf.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ivf.dto.UserDTO;
import com.ivf.entitis.UserEntity;
import com.ivf.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenService tokenService;

	private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Override
	public UserDTO login(String email, String password) {

		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
		}

		boolean matches = bCryptPasswordEncoder.matches(password, userEntity.getPassword());

		if (!matches) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "The current password you entered is incorrect");
		}

		String token = tokenService.createToken(userEntity.getEmail(), userEntity.getRole());

		UserDTO userDTO = mapToDTO(userEntity, token);

		return userDTO;
	}

	@Override
	public UserDTO changePassword(UserDTO userDTO) {
		UserEntity userEntity = userRepository.findByEmail(userDTO.getEmail());

		if (userEntity == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
		}

		boolean matches = bCryptPasswordEncoder.matches(userDTO.getPassword(), userEntity.getPassword());

		if (!matches) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"The current password you entered is incorrect.");
		}

		String encodedPassword = bCryptPasswordEncoder.encode(userDTO.getNewPassword());

		userEntity.setPassword(encodedPassword);

		userEntity = userRepository.save(userEntity);

		return mapToDTO(userEntity, null);

	}

	@Override
	public List<UserDTO> findByRole(UserDTO userDTO) {
		List<UserEntity> userEntities = userRepository.findByRole(userDTO.getRole());

		List<UserDTO> userDTOs = new ArrayList<UserDTO>();

		if (userEntities != null) {
			for (UserEntity userEntity : userEntities) {
				userDTOs.add(mapToDTO(userEntity, null));
			}
		}

		return userDTOs;
	}

	private UserDTO mapToDTO(UserEntity user, String token) {

		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setEmail(user.getEmail());
		userDTO.setPhoneNumber(user.getPhoneNumber());
		userDTO.setRole(user.getRole());
		userDTO.setToken(token);

		return userDTO;
	}

	private UserEntity mapToEntity(UserDTO userDTO) {

		UserEntity userEntity = new UserEntity();
		userEntity.setName(userDTO.getName());
		userEntity.setEmail(userDTO.getEmail());
		userEntity.setPhoneNumber(userDTO.getPhoneNumber());
		userEntity.setRole(userDTO.getRole());
		userEntity.setPassword(userDTO.getPassword());

		return userEntity;
	}

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String rawPassword = "password";
		String hashedPassword = encoder.encode(rawPassword);

		System.out.println(hashedPassword);
	}

}
