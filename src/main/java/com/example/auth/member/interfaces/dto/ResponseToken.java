package com.example.auth.member.interfaces.dto;

import static com.google.common.base.Preconditions.checkArgument;

public class ResponseToken {
	private final String token;
	
	private final MemberDto user;
	
	public ResponseToken(String token, MemberDto user) {
		checkArgument(token != null, "apiToken must be provided.");
		checkArgument(user != null, "user must be provided.");
		
		this.token = token;
		this.user = user;
	}
	
	public String getToken() {
		return token;
	}
	
	public MemberDto getUser() {
		return user;
	}
}
