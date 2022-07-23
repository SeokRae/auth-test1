package com.example.auth.core.security;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class JwtAuthentication {
	
	private final Long id;
	private final String email;
	
	JwtAuthentication(Long id, String email) {
		checkArgument(id != null, "id must be provided.");
		checkArgument(isNotEmpty(email), "email must be provided.");
		this.id = id;
		this.email = email;
	}
	
	public Long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	
	@Override
	public String toString() {
		return "JwtAuthentication{" +
			"id=" + id +
			", email='" + email + '\'' +
			'}';
	}
}