package com.example.auth.core.config;

import com.example.auth.core.security.Jwt;
import com.example.auth.core.security.JwtAuthenticationFilter;
import com.example.auth.core.security.JwtAuthenticationProvider;
import com.example.auth.core.security.config.JwtConfig;
import com.example.auth.core.security.handler.EntryPointUnauthorizedHandler;
import com.example.auth.core.security.handler.JwtAccessDeniedHandler;
import com.example.auth.member.application.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;

@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final JwtConfig jwtConfig;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable(); // 가장 먼저 설정해줘야 함
		
		http
			.formLogin().disable()
			.cors().disable()
			.httpBasic().disable()
			.headers().disable()
			.rememberMe().disable()
			.logout().disable()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http
			.authorizeHttpRequests()
			.antMatchers("/assets/**", "/h2-console/**").permitAll()
			.antMatchers(HttpMethod.POST, "/v1/member/signUp", "/v1/member/signIn").permitAll()
			.antMatchers(HttpMethod.GET, "/v1/member/me").hasRole("USER")
			.anyRequest().authenticated();
		
		http
			.exceptionHandling()
			.accessDeniedHandler(jwtAccessDeniedHandler)
			.authenticationEntryPoint(entryPointUnauthorizedHandler);
		
		http
			.addFilterAt(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	public Jwt jwt() {
		return new Jwt(
			jwtConfig.getIssuer(),
			jwtConfig.getClientSecret(),
			jwtConfig.getExpirySeconds()
		);
	}
	
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		Jwt jwt = getApplicationContext().getBean(Jwt.class);
		return new JwtAuthenticationFilter(jwtConfig.getHeader(), jwt);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public JwtAuthenticationProvider jwtAuthenticationProvider(UserService userService, Jwt jwt) {
		return new JwtAuthenticationProvider(jwt, userService);
	}
	
	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder auth, JwtAuthenticationProvider authenticationProvider) {
		auth.authenticationProvider(authenticationProvider);
	}
	
}
