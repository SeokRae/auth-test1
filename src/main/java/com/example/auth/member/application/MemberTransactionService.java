package com.example.auth.member.application;

import com.example.auth.member.domain.Member;
import com.example.auth.member.infrastructure.MemberRepository;
import com.example.auth.member.interfaces.dto.RequestSaveMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberTransactionService implements MemberCommandService {
	
	private final MemberRepository memberRepository;
	
	@Override
	public Member saveMember(RequestSaveMember saveMember) {
		Member member = new Member(saveMember.getEmail(), saveMember.getPassword());
		return memberRepository.save(member);
	}
}
