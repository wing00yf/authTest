package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.service.AuthService;

public class AuthController {
	
	private static final long TOKEN_EXPIRY_TIME = 2 * 60 * 60 * 1000; // 2 hours in milliseconds
    
	private Map<String, Long> tokenCreationTimes = new HashMap<>();
	
	private AuthService authService = new AuthService();

	public void createUser(String username, String password) {
		authService.createUser(username, password);
	}
	
	public void deleteUser(String username) {
		authService.deleteUser(username);
	}
	
	public void createRole(String roleName) {
		authService.createRole(roleName);
	}
	
	public void deleteRole(String roleName) {
		authService.deleteRole(roleName);
	}
	
	public void addRoleToUser(String username, String roleName) {
		authService.addRoleToUser(username, roleName);
	}
	
	public String authenticate(String username, String password) {
		 String token = authService.authenticate(username, password);
		//add token expired time
	     tokenCreationTimes.put(token, System.currentTimeMillis());
	     return token;
	}
	
	public void invalidate(String token) {
		//check token expired first
		validateToken(token);
		authService.invalidate(token);
	}
	
	public boolean checkRole(String token, String roleName) {
		//check token expired first
		validateToken(token);
		return authService.checkRole(token, roleName);
	}
	
	public List<String> allRoles(String token) {
		//check token expired first
		validateToken(token);
		return authService.allRoles(token);
	}
	
	//check token is invalid
	private void validateToken(String token) {
        Long creationTime = tokenCreationTimes.get(token);
        if (creationTime == null || System.currentTimeMillis() - creationTime > TOKEN_EXPIRY_TIME) {
            throw new IllegalArgumentException("Invalid or expired token");
        }
    }
}
