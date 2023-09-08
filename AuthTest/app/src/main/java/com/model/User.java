package com.model;

public class User {
	private String username;
	private String hashedPassword;
    private String token;
    
    public User(String username, String hashed) {
		this.username= username;
		this.hashedPassword = hashed;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getHashedPassword() {
		return hashedPassword;
	}
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
