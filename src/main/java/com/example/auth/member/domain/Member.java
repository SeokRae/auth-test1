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
	
	@Builder
	public Member(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
