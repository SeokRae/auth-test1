package com.example.auth.member.application;

import com.example.auth.member.interfaces.dto.ResponseMember;

public interface MemberQueryService {
	
	ResponseMember findMemberByUsername(String username);
	
	ResponseMember findMemberByEmail(String email);
}
