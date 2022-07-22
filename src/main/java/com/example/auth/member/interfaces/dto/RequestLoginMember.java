package com.example.auth.member.interfaces.dto;

public class RequestLoginMember {
	
	private String email;
	
	private String password;
	
	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}
	
	@Override
	public String toString() {
		return "RequestLoginMember{" +
			"email='" + email + '\'' +
			", password='" + password + '\'' +
			'}';
	}
}
