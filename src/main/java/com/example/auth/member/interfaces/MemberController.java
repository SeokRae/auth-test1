package com.example.auth.member.interfaces;

import com.example.auth.core.security.JwtAuthentication;
import com.example.auth.core.security.JwtAuthenticationToken;
import com.example.auth.member.application.MemberCommandService;
import com.example.auth.member.application.MemberQueryService;
import com.example.auth.member.interfaces.dto.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.example.auth.member.interfaces.dto.ApiResult.OK;

@Slf4j
@Api(tags = "사용자 APIs")
@RestController
@RequestMapping(path = "/v1/member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberCommandService memberCommandService;
	private final MemberQueryService memberQueryService;
	private final AuthenticationManager authenticationManager;
	
	@PostMapping(path = "/signUp")
	@ApiOperation(value = "사용자 등록 (API 토큰 필요없음)")
	public ApiResult<MemberDto> signUp(@RequestBody RequestSaveMember saveMember) {
		MemberDto savedMember = memberCommandService.saveMember(saveMember.getEmail(), saveMember.getPassword());
		log.info("signUp : {}", savedMember);
		return OK(savedMember);
	}
	
	@PostMapping(path = "/signIn")
	@ApiOperation(value = "로그인 (API 토큰 필요없음)")
	public ApiResult<ResponseToken> signIn(@RequestBody RequestLoginMember login) {
		JwtAuthenticationToken token = new JwtAuthenticationToken(login.getEmail(), login.getPassword());
		Authentication authentication = authenticationManager.authenticate(token);
		
		ResponseToken member = (ResponseToken) authentication.getDetails();
		// 토큰 발급
		log.info("signIn");
		
		return OK(new ResponseToken(member.getToken(), member.getUser()));
	}
	
	@GetMapping(path = "/me")
	@ApiOperation(value = "프로필 조회 (API 토큰 필요없음)")
	public ApiResult<MemberDto> me(@AuthenticationPrincipal JwtAuthentication jwtAuthentication) {
		MemberDto responseMember = memberQueryService.findMemberByEmail(jwtAuthentication.getEmail());
		log.info("getProfile : {}", responseMember);
		return OK(responseMember);
	}
}
