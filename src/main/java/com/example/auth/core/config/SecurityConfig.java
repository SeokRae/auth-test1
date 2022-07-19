package com.example.auth.core.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // 가장 먼저 설정해줘야 함
			.authorizeHttpRequests()
				.antMatchers(HttpMethod.POST, "/v1/member/signup", "/v1/member/login").permitAll()
			.anyRequest().authenticated();
	}
}
