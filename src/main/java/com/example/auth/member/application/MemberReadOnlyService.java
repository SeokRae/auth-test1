package com.example.auth.member.application;

import com.example.auth.member.domain.Member;
import com.example.auth.member.exception.NotFoundException;
import com.example.auth.member.infrastructure.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReadOnlyService implements MemberQueryService {
	private final MemberRepository memberRepository;
	
	@Override
	public Member findMemberByUsername(String username) {
		return memberRepository.findByUsername(username)
			.orElseThrow(NotFoundException::new);
	}
}
