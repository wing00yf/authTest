package com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.mindrot.jbcrypt.BCrypt;

import com.model.AuthData;
import com.model.Role;
import com.model.User;

public class AuthService {
	
	private Map<String, String> tokenToUsernameMap = new HashMap<>();
	
	private AuthData authData = new AuthData();
	
	public User createUser(String username, String password) {	
        if (authData.containsUser(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        User user = new User(username, hashed);
        authData.addUser(user);
        return user;
    }
	
	public User getUser(String username) {
		return authData.getUser(username);
	}

    public void deleteUser(String username) {
        if (!authData.containsUser(username)) {
            throw new IllegalArgumentException("User does not exist");
        }
        authData.removeUser(username,tokenToUsernameMap);
    }

    public Role createRole(String roleName) {
    	Role role = authData.createRole(roleName);
    	authData.addRole(role);
    	return role;
    }
    
    public List<String> getRoles(String roleName) {
    	return authData.getUserRoles(roleName);
    }

    public void deleteRole(String roleName) {
    	authData.deleteRole(roleName);
    }

    public void addRoleToUser(String username, String roleName) {
    	authData.addRoleToUser(username,roleName);
    }

    public String authenticate(String username, String password) {
        User user = authData.getUser(username);
        if (user != null && BCrypt.checkpw(password, user.getHashedPassword())) {
            String token = RandomStringUtils.randomAlphanumeric(20);
            user.setToken(token);
            tokenToUsernameMap.put(token, username);
            return token;
        } else {
            throw new IllegalArgumentException("Authentication failed");
        }
    }

    public void invalidate(String token) {
        String username = tokenToUsernameMap.get(token);
        if (username != null) {
        	authData.getUser(username).setToken(null);
            tokenToUsernameMap.remove(token);
        }
    }

    public boolean checkRole(String token, String roleName) {
        String username = tokenToUsernameMap.get(token);
        if (username == null) {
            throw new IllegalArgumentException("Invalid token");
        }
        List<String> roles = authData.getUserRoles(username);
        return roles != null && roles.contains(roleName);
    }

    public List<String> allRoles(String token) {
        String username = tokenToUsernameMap.get(token);
        if (username == null) {
            throw new IllegalArgumentException("Invalid token");
        }
        return authData.getUserRoles(username);
    }
}
