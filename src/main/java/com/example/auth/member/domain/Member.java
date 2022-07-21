package com.example.auth.member.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "TB_MEMBER")
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	
	private String email;
	
	private String password;
	
	@Enumerated(EnumType.STRING)
	private Role role;
	
	public Member() {
	}
	
	@Builder
	public Member(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	@Builder
	public Member(String email, String password, Role role) {
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	public void checkPassword(PasswordEncoder passwordEncoder, String credentials) {
		if (!passwordEncoder.matches(credentials, password))
			throw new IllegalArgumentException("Bad credential");
	}
}
