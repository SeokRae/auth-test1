package com.example.auth.member.interfaces.dto;

import com.example.auth.member.domain.Member;
import com.example.auth.member.domain.Role;
import lombok.Getter;

@Getter
public class MemberDto {
	private final Long id;
	private final String username;
	private final String email;
	private final Role role;
	
	public MemberDto(Member m) {
		this.id = m.getId();
		this.email = m.getEmail();
		this.username = m.getUsername();
		this.role = m.getRole();
	}
	
	@Override
	public String toString() {
		return "MemberDto{" +
			"id=" + id +
			", username='" + username + '\'' +
			", email='" + email + '\'' +
			", role=" + role +
			'}';
	}
}
