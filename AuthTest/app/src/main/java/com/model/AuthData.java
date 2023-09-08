package com.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthData {
	private Map<String, User> users = new HashMap<>();
    private Map<String, Role> roles = new HashMap<>();
    private Map<String, List<String>> userToRolesMap = new HashMap<>();
    
    
    public User getUser(String username) {
    	return users.get(username);
    }
    
    public boolean containsUser(String username) {
    	return users.containsKey(username);
    }
    
    public void addUser(User user) {
    	if (users.containsKey(user.getUsername())) {
              throw new IllegalArgumentException("Username already exists");
        }
    	users.put(user.getUsername(), user);
    }
    
    public void removeUser(String username, Map<String, String> tokenToUsernameMap) {
    	if (!users.containsKey(username)) {
            throw new IllegalArgumentException("User does not exist");
        }
    	tokenToUsernameMap.remove(users.get(username).getToken());
    	users.remove(username);
        userToRolesMap.remove(username);
    }
    
    public List<String> getUserRoles(String roleName) {
    	if (roles.containsKey(roleName)) {
            throw new IllegalArgumentException("Role already exists");
        }
    	return userToRolesMap.get(roleName);
    }
    
    public Role createRole(String roleName) {
    	if (roles.containsKey(roleName)) {
            throw new IllegalArgumentException("Role already exists");
        }
    	Role role = new Role(roleName);
        return role;
    }
    
    public void addRole(Role role) {
    	if (roles.containsKey(role.getRoleName())) {
            throw new IllegalArgumentException("Role already exists");
        }
    	roles.put(role.getRoleName(), role);
    }
    
    public void deleteRole(String roleName) {
        if (!roles.containsKey(roleName)) {
            throw new IllegalArgumentException("Role does not exist");
        }
        roles.remove(roleName);
        userToRolesMap.values().forEach(roles -> roles.remove(roleName));
    }
    
    public void addRoleToUser(String username, String roleName) {
        if (!users.containsKey(username)) {
            throw new IllegalArgumentException("User does not exist");
        }
        if (!roles.containsKey(roleName)) {
            throw new IllegalArgumentException("Role does not exist");
        }
        userToRolesMap.putIfAbsent(username, new ArrayList<>());
        userToRolesMap.get(username).add(roleName);
    }
    
    
}
