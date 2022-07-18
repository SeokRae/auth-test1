package com.example.auth.member.application;

import com.example.auth.member.domain.Member;

public interface MemberQueryService {

	Member findMemberByUsername(String username);
}
