package com.example.auth.member.domain;

import lombok.Builder;
import lombok.Getter;

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
	
	public Member() {
	}
	
	public Member(String email, String password) {
		this(null, null, email, password);
	}
	
	public Member(String username, String email, String password) {
		this(null, username, email, password);
	}
	
	@Builder
	public Member(Long id, String username, String email, String password) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
	}
}
