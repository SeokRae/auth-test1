package com.example.auth.member.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestLoginMember {
	
	private String email;
	
	private String password;
	
	@Override
	public String toString() {
		return "RequestLoginMember{" +
			"email='" + email + '\'' +
			", password='" + password + '\'' +
			'}';
	}
}
