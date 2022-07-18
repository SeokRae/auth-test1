package com.example.auth.member.application;

import com.example.auth.member.domain.Member;
import com.example.auth.member.infrastructure.MemberRepository;
import com.example.auth.member.interfaces.dto.RequestSaveMember;
import com.example.auth.member.interfaces.dto.ResponseMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberTransactionService implements MemberCommandService {

	private final MemberRepository memberRepository;
	
	@Override
	public ResponseMember saveMember(RequestSaveMember saveMember) {
		Member member = new Member(saveMember.getEmail(), saveMember.getPassword());
		return Optional.of(memberRepository.save(member))
			.map(ResponseMember::new)
			.orElseThrow(RuntimeException::new);
	}
}
