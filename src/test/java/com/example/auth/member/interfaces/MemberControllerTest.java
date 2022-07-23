package com.example.auth.member.interfaces;

import com.example.auth.core.config.SecurityConfig;
import com.example.auth.core.security.WithMockJwtAuthentication;
import com.example.auth.core.security.config.JwtConfig;
import com.example.auth.core.security.handler.EntryPointUnauthorizedHandler;
import com.example.auth.core.security.handler.JwtAccessDeniedHandler;
import com.example.auth.member.application.MemberQueryService;
import com.example.auth.member.application.UserService;
import com.example.auth.member.domain.Member;
import com.example.auth.member.infrastructure.MemberRepository;
import com.example.auth.member.interfaces.dto.MemberDto;
import com.example.auth.member.interfaces.dto.RequestLoginMember;
import com.example.auth.member.interfaces.dto.RequestSaveMember;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class MemberControllerTest {
	private final String responsePath = "$.response";
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	@Order(1)
	@Test
	void test1() throws Exception {
		
		ResultActions result = mockMvc.perform(
			post("/v1/member/signUp")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(new RequestSaveMember("tester00@gmail.com", "1234")))
		);
		result
			.andExpect(status().isOk())
			.andExpect(handler().handlerType(MemberController.class))
			.andExpect(handler().methodName("signUp"))
			.andDo(print());
	}
	
	@Order(2)
	@Test
	void test2() throws Exception {
		
		ResultActions result = mockMvc.perform(
			post("/v1/member/signIn")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(new RequestLoginMember("tester00@gmail.com", "1234")))
		);
		result
			.andExpect(status().isOk())
			.andExpect(handler().handlerType(MemberController.class))
			.andExpect(handler().methodName("signIn"))
			.andDo(print());
	}
	
	@Order(3)
	@Test
	@WithMockJwtAuthentication
	@DisplayName("내 정보 조회 성공 테스트 (토큰이 올바른 경우)")
	void test3() throws Exception {
		ResultActions result = mockMvc.perform(
			get("/v1/member/me")
		);
		result
			.andExpect(status().isOk())
			.andDo(print())
			.andExpect(handler().handlerType(MemberController.class))
			.andExpect(handler().methodName("me"))
			.andExpectAll(validUserModel(responsePath, "tester00", "test00@gmail.com"));
	}
	
	private ResultMatcher validUserModel(String path, String name, String address) {
		return result -> {
			jsonPath("$.success", is(true));
		};
	}
}