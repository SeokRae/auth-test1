package com.example.auth.member.interfaces.dto;

import com.example.auth.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberDto {
	private final Long id;
	private final String username;
	private final String email;
	
	private final String token;
	
	private final String group;
	
	public MemberDto(Member m) {
		this(m, null);
	}
	
	public MemberDto(Member m, String token) {
		this.id = m.getId();
		this.email = m.getEmail();
		this.username = m.getUsername();
		this.group = m.getGroup().getName();
		this.token = token;
	}
	
	@Override
	public String toString() {
		return "ResponseMember{" +
			"id=" + id +
			", username='" + username + '\'' +
			", email='" + email + '\'' +
			", token='" + token + '\'' +
			", group='" + group + '\'' +
			'}';
	}
}
