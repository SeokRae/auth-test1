package com.example.auth.member.interfaces;

import com.example.auth.member.application.MemberCommandService;
import com.example.auth.member.application.MemberQueryService;
import com.example.auth.member.interfaces.dto.RequestMe;
import com.example.auth.member.interfaces.dto.RequestSaveMember;
import com.example.auth.member.interfaces.dto.ResponseMember;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/v1/member")
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberCommandService memberCommandService;
	private final MemberQueryService memberQueryService;
	
	@PostMapping(path = "/signup")
	public ResponseEntity<ResponseMember> signUp(@RequestBody RequestSaveMember saveMember) {
		ResponseMember responseMember = memberCommandService.saveMember(saveMember);
		log.info("signUp : {}", responseMember);
		return ResponseEntity.ok().body(responseMember);
	}
	
	@PostMapping(path = "/signIn")
	public void signIn() {
		// 토큰 발급
		log.info("signIn");
	}
	
	@PostMapping(path = "/me")
	public ResponseEntity<ResponseMember> getProfile(@RequestBody RequestMe requestMe) {
		ResponseMember responseMember = memberQueryService.findMemberByEmail(requestMe.getEmail());
		log.info("getProfile : {}", responseMember);
		return ResponseEntity.ok(responseMember);
	}
}
