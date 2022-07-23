package com.example.auth.member.interfaces.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestSaveMember {
	private String email;
	private String password;
	
}
