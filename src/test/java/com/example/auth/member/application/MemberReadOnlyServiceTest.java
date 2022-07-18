package com.example.auth.member.application;

import com.example.auth.member.domain.Member;
import com.example.auth.member.infrastructure.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberReadOnlyServiceTest {
	
	@InjectMocks
	private MemberReadOnlyService memberReadOnlyService;
	
	@Mock
	private MemberRepository memberRepository;
	
	@DisplayName("사용자 쿼리 테스트")
	@Test
	void testCase1(@Mock Member member) {
		
		given(memberRepository.findByUsername(anyString())).willReturn(Optional.ofNullable(member));
		Member expected = memberReadOnlyService.findMemberByUsername(anyString());
		
		assertThat(member).isEqualTo(expected);
	}
	
}