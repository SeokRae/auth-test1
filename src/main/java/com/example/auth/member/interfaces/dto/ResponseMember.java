package com.example.auth.member.interfaces.dto;

import com.example.auth.member.domain.Member;
import lombok.Getter;

@Getter
public class ResponseMember {
	private final Long id;
	private final String username;
	private final String email;
	
	public ResponseMember(Member m) {
		this.id = m.getId();
		this.email = m.getEmail();
		this.username = m.getUsername();
	}
	
	@Override
	public String toString() {
		return "ResponseMember{" +
			"id=" + id +
			", username='" + username + '\'' +
			", email='" + email + '\'' +
			'}';
	}
}
