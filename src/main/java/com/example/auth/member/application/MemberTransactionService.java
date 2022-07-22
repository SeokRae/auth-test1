package com.example.auth.member.application;

import com.example.auth.member.domain.Member;
import com.example.auth.member.domain.Role;
import com.example.auth.member.infrastructure.MemberRepository;
import com.example.auth.member.interfaces.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
@RequiredArgsConstructor
public class MemberTransactionService implements MemberCommandService {
	
	private final MemberRepository memberRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	
	@Override
	public MemberDto saveMember(String email, String password) {
		checkArgument(isNotEmpty(password), "password must be provided.");
		return Optional.of(memberRepository.save(new Member(email, password, Role.ROLE_USER.name(), passwordEncoder)))
			.map(MemberDto::new)
			.orElseThrow(RuntimeException::new);
	}
}
