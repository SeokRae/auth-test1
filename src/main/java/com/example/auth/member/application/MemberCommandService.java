package com.example.auth.member.application;

import com.example.auth.member.interfaces.dto.ResponseMember;
import com.example.auth.member.interfaces.dto.RequestSaveMember;

public interface MemberCommandService {
	ResponseMember saveMember(RequestSaveMember saveMember);
	
}
