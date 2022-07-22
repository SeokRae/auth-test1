package com.example.auth.member.interfaces;

import com.example.auth.core.security.JwtAuthentication;
import com.example.auth.core.security.JwtAuthenticationToken;
import com.example.auth.member.application.MemberCommandService;
import com.example.auth.member.application.MemberQueryService;
import com.example.auth.member.domain.Member;
import com.example.auth.member.interfaces.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.example.auth.member.interfaces.dto.ApiResult.OK;

@Slf4j
@RestController
@RequestMapping(path = "/v1/member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberCommandService memberCommandService;
	private final MemberQueryService memberQueryService;
	private final AuthenticationManager authenticationManager;
	
	@PostMapping(path = "/signup")
	public ApiResult<MemberDto> signUp(@RequestBody RequestSaveMember saveMember) {
		MemberDto savedMember = memberCommandService.saveMember(saveMember.getEmail(), saveMember.getPassword());
		log.info("signUp : {}", savedMember);
		return OK(savedMember);
	}
	
	@PostMapping(path = "/signin")
	public ApiResult<ResponseToken> signIn(@RequestBody RequestLoginMember login) {
		JwtAuthenticationToken token = new JwtAuthenticationToken(login.getEmail(), login.getPassword());
		Authentication authentication = authenticationManager.authenticate(token);
		
		JwtAuthentication auth = (JwtAuthentication) authentication.getPrincipal();
		Member member = (Member) authentication.getDetails();
		// 토큰 발급
		log.info("signIn");
		return OK(new ResponseToken(auth.getToken(), new MemberDto(member)));
	}
	
	@GetMapping(path = "/me")
	public ApiResult<MemberDto> getProfile(@AuthenticationPrincipal JwtAuthentication jwtAuthentication) {
		MemberDto responseMember = memberQueryService.findMemberByEmail(jwtAuthentication.getUsername());
		log.info("getProfile : {}", responseMember);
		return OK(responseMember);
	}
}
