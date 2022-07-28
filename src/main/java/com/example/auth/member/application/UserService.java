package com.example.auth.member.application;

import com.example.auth.member.domain.Member;
import com.example.auth.member.infrastructure.MemberRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Service
public class UserService {
	
	private final PasswordEncoder passwordEncoder;
	
	private final MemberRepository userRepository;
	
	public UserService(PasswordEncoder passwordEncoder, MemberRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}
	
	@Transactional(readOnly = true)
	public Member login(String principal, String credentials) {
		checkArgument(isNotEmpty(principal), "principal must be provided.");
		checkArgument(isNotEmpty(credentials), "credentials must be provided.");
		
		Member user = userRepository.findByEmail(principal)
			.orElseThrow(() -> new UsernameNotFoundException("Could not found user for " + principal));
		user.checkPassword(passwordEncoder, credentials);
		return user;
	}
}