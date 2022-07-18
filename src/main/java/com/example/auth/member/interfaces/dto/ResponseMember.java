package com.example.auth.member.interfaces.dto;

import com.example.auth.member.domain.Member;
import lombok.Getter;

@Getter
public class ResponseMember {
	private final String username;
	private final String email;
	
	public ResponseMember(Member m) {
		this.email = m.getEmail();
		this.username = m.getUsername();
	}
	
	@Override
	public String toString() {
		return "MemberResponse{" +
			"username='" + username + '\'' +
			", email='" + email + '\'' +
			'}';
	}
}
