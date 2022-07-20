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
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "group_id")
	private Group group;
	
	public Member() {
	}
	
	@Builder
	public Member(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public void checkPassword(PasswordEncoder passwordEncoder, String credentials) {
		if (!passwordEncoder.matches(credentials, password))
			throw new IllegalArgumentException("Bad credential");
	}
}
