package com.ivf.services;

public interface TokenService {
	

	public String createToken(String userName, String role);

	public String extractUsername(String token);

	public String extractRole(String token);
	
	public boolean isTokenExpired(String token);
}
