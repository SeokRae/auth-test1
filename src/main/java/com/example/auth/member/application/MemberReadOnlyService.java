package com.example.auth.member.application;

import com.example.auth.member.exception.NotFoundException;
import com.example.auth.member.infrastructure.MemberRepository;
import com.example.auth.member.interfaces.dto.ResponseMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberReadOnlyService implements MemberQueryService {
	private final MemberRepository memberRepository;
	
	@Override
	public ResponseMember findMemberByUsername(String username) {
		return memberRepository.findByUsername(username)
			.map(ResponseMember::new)
			.orElseThrow(NotFoundException::new);
	}
	
	@Override
	public ResponseMember findMemberByEmail(String email) {
		return memberRepository.findByEmail(email)
			.map(ResponseMember::new)
			.orElseThrow(NotFoundException::new);
	}
}
