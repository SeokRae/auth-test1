package com.example.auth.member.application;

import com.example.auth.member.interfaces.dto.MemberDto;

public interface MemberQueryService {
	
	MemberDto findMemberByUsername(String username);
	
	MemberDto findMemberByEmail(String email);
}
