package com.example.auth.member.interfaces.dto;

import com.example.auth.member.domain.Role;

public class ResponseToken {
	private final String email;
	private final String role;
	private final String token;
	
	public ResponseToken(String email, String role, String token) {
		this.email = email;
		this.role = role;
		this.token = token;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getRole() {
		return role;
	}
	
	public String getToken() {
		return token;
	}
}
