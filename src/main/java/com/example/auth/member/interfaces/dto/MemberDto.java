package com.example.auth.member.interfaces.dto;

import com.example.auth.member.domain.Member;
import lombok.Getter;

@Getter
public class MemberDto {
	private Long id;
	private String email;
	private String role;
	
	protected MemberDto() {}
	
	public MemberDto(Member m) {
		this.id = m.getId();
		this.email = m.getEmail();
		this.role = m.getRole();
	}
	
	@Override
	public String toString() {
		return "MemberDto{" +
			"id=" + id +
			", email='" + email + '\'' +
			", role='" + role + '\'' +
			'}';
	}
}
