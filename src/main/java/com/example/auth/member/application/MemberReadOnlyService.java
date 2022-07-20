package com.example.auth.member.application;

import com.example.auth.member.exception.NotFoundException;
import com.example.auth.member.infrastructure.MemberRepository;
import com.example.auth.member.interfaces.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberReadOnlyService implements MemberQueryService {
	private final MemberRepository memberRepository;
	
	@Override
	public MemberDto findMemberByUsername(String username) {
		return memberRepository.findByUsername(username)
			.map(MemberDto::new)
			.orElseThrow(NotFoundException::new);
	}
	
	@Override
	public MemberDto findMemberByEmail(String email) {
		return memberRepository.findByEmail(email)
			.map(MemberDto::new)
			.orElseThrow(NotFoundException::new);
	}
}
