package com.example.auth.member.application;

import com.example.auth.member.domain.Member;
import com.example.auth.member.interfaces.dto.RequestSaveMember;

public interface MemberCommandService {
	Member saveMember(RequestSaveMember saveMember);
	
}
