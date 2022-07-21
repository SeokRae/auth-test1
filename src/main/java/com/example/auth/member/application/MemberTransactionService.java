package com.example.auth.member.application;

import com.example.auth.member.domain.Member;
import com.example.auth.member.infrastructure.MemberRepository;
import com.example.auth.member.interfaces.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberTransactionService implements MemberCommandService {
	
	private final MemberRepository memberRepository;
	
	@Override
	public MemberDto saveMember(String email, String password) {
		return Optional.of(memberRepository.save(new Member(email, password)))
			.map(MemberDto::new)
			.orElseThrow(RuntimeException::new);
	}
}
