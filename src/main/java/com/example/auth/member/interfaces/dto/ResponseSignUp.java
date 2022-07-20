package com.example.auth.member.interfaces.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkArgument;

public class ResponseSignUp {
	
	private final String apiToken;
	
	private final MemberDto user;
	
	public ResponseSignUp(String apiToken, MemberDto user) {
		checkArgument(apiToken != null, "apiToken must be provided.");
		checkArgument(user != null, "user must be provided.");
		
		this.apiToken = apiToken;
		this.user = user;
	}
	
	public String getApiToken() {
		return apiToken;
	}
	
	public MemberDto getUser() {
		return user;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("apiToken", apiToken)
			.append("member", user)
			.toString();
	}
	
}