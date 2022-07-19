package com.example.auth.core.security;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JwtTest {
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	private Jwt jwt;
	
	@BeforeAll
	void setUp() {
		String issuer = "social-server";
		String clientSecret = "Rel5Bjce6MajBo08qgkNgYaTuzvJe6iwnBFhsD11";
		int expirySeconds = 10;
		
		jwt = new Jwt(issuer, clientSecret, expirySeconds);
	}
	
	@DisplayName("기본 Claims 정보 조회 확인")
	@Test
	void testCase1() {
		assertThat(jwt.getIssuer()).isEqualTo("social-server");
		assertThat(jwt.getClientSecret()).isEqualTo("Rel5Bjce6MajBo08qgkNgYaTuzvJe6iwnBFhsD11");
		assertThat(jwt.getExpirySeconds()).isEqualTo(10);
	}
	
	@DisplayName("JWT 생성 검증")
	@Test
	void testCase2() {
		Jwt.Claims claims = Jwt.Claims.from("tester", new String[]{"ROLE_USER"});
		assertThat(claims.username).isEqualTo("tester");
		assertThat(claims.roles).isEqualTo(new String[]{"ROLE_USER"});
	}
	
	@DisplayName("JWT_토큰을_생성하고_복호화_할수있다")
	@Test
	void testCase3() {
		Jwt.Claims claims = Jwt.Claims.from("tester", new String[]{"ROLE_USER"});
		String encodedJWT = jwt.sign(claims);
		log.info("encodedJWT: {}", encodedJWT);
		
		Jwt.Claims decodedJWT = jwt.verify(encodedJWT);
		log.info("decodedJWT: {}", decodedJWT);
		
		assertThat(claims.username).isEqualTo(decodedJWT.username);
		assertArrayEquals(claims.roles, decodedJWT.roles);
	}
	
	@DisplayName("JWT_토큰을_리프레시_할수있다")
	@Test
	void testCase4() throws Exception {
		if (jwt.getExpirySeconds() > 0) {
			Jwt.Claims claims = Jwt.Claims.from("tester", new String[]{"ROLE_USER"});
			String encodedJWT = jwt.sign(claims);
			log.info("encodedJWT: {}", encodedJWT);
			
			// 1초 대기 후 토큰 갱신
			sleep(1_000L);
			
			String encodedRefreshedJWT = jwt.refreshToken(encodedJWT);
			log.info("encodedRefreshedJWT: {}", encodedRefreshedJWT);
			
			assertThat(encodedJWT).isNotEqualTo(encodedRefreshedJWT);
			
			Jwt.Claims oldJwt = jwt.verify(encodedJWT);
			Jwt.Claims newJwt = jwt.verify(encodedRefreshedJWT);
			
			long oldExp = oldJwt.exp();
			long newExp = newJwt.exp();
			
			// 1초 후에 토큰을 갱신했으므로, 새로운 토큰의 만료시각이 1초 이후임
			assertThat(newExp >= oldExp + 1_000L).isTrue();
			
			log.info("oldJwt: {}", oldJwt);
			log.info("newJwt: {}", newJwt);
		}
	}
	
	@DisplayName("payload 데이터 확인")
	@Test
	void testCase5() {
		Jwt.Claims claims = Jwt.Claims.from("tester", new String[]{"ROLE_USER"});
		Map<String, Object> stringObjectMap = claims.asMap();
		assertAll(
			() -> assertThat(stringObjectMap).containsEntry("username", "tester"),
			() -> assertThat(stringObjectMap).containsEntry("roles", new String[]{"ROLE_USER"})
		);
	}
	
}