package com.example.auth.member.interfaces;

import com.example.auth.member.application.MemberCommandService;
import com.example.auth.member.application.MemberQueryService;
import com.example.auth.member.interfaces.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.example.auth.member.interfaces.dto.ApiResult.OK;

@Slf4j
@RestController
@RequestMapping(path = "/v1/member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberCommandService memberCommandService;
	private final MemberQueryService memberQueryService;
	
	@PostMapping(path = "/signup")
	public ApiResult<ResponseSignUp> signUp(@RequestBody RequestSaveMember saveMember) {
		ResponseSignUp responseMember = Optional.of(memberCommandService.saveMember(saveMember))
			.map(member -> new ResponseSignUp(null, new MemberDto(member)))
			.orElseThrow(RuntimeException::new);
		log.info("signUp : {}", responseMember);
		return OK(responseMember);
	}
	
	@PostMapping(path = "/signIn")
	public void signIn() {
		// 토큰 발급
		log.info("signIn");
	}
	
	@PostMapping(path = "/me")
	public ApiResult<MemberDto> getProfile(@RequestBody RequestMe requestMe) {
		MemberDto responseMember = memberQueryService.findMemberByEmail(requestMe.getEmail());
		log.info("getProfile : {}", responseMember);
		return ApiResult.OK(responseMember);
	}
}
