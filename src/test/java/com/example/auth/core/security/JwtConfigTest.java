package com.example.auth.core.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = JwtConfig.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class JwtConfigTest {
	
	@Autowired
	private JwtConfig jwtConfig;
	
	@Test
	void testCase1() {
		assertAll(
			() -> assertThat(jwtConfig.getIssuer()).isNotBlank(),
			() -> assertThat(jwtConfig.getHeader()).isNotBlank(),
			() -> assertThat(jwtConfig.getExpirySeconds()).isPositive(),
			() -> assertThat(jwtConfig.getClientSecret()).isNotBlank()
			);
	}
}