package com.example.auth.member.interfaces;

import com.example.auth.core.security.WithMockJwtAuthentication;
import com.example.auth.member.interfaces.dto.RequestLoginMember;
import com.example.auth.member.interfaces.dto.RequestSaveMember;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.hamcrest.Matchers.is;
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
	@DisplayName("사용자 등록 테스트")
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
		;
	}
	
	@Order(2)
	@Test
	@DisplayName("사용자 로그인 테스트")
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