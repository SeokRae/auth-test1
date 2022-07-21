package com.example.auth.member.application;

import com.example.auth.member.interfaces.dto.MemberDto;

public interface MemberCommandService {
	MemberDto saveMember(String email, String password);
}
