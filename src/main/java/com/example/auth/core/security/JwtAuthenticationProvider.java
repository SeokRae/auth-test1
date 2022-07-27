package com.example.auth.core.security;

import com.example.auth.member.application.UserService;
import com.example.auth.member.domain.Member;
import com.example.auth.member.interfaces.dto.MemberDto;
import com.example.auth.member.interfaces.dto.ResponseToken;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

import static org.apache.commons.lang3.ClassUtils.isAssignable;

public class JwtAuthenticationProvider implements AuthenticationProvider {
	
	private final Jwt jwt;
	
	private final UserService userService;
	
	public JwtAuthenticationProvider(Jwt jwt, UserService userService) {
		this.jwt = jwt;
		this.userService = userService;
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		JwtAuthenticationToken jwtAuthentication = (JwtAuthenticationToken) authentication;
		return processUserAuthentication(
			String.valueOf(jwtAuthentication.getPrincipal()),
			jwtAuthentication.getCredentials()
		);
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return isAssignable(JwtAuthenticationToken.class, authentication);
	}
	
	private Authentication processUserAuthentication(String principal, String credentials) {
		try {
			Member user = userService.login(principal, credentials);
			List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
			JwtAuthenticationToken authenticated = new JwtAuthenticationToken(new JwtAuthentication(user.getId(), user.getEmail()), null, authorities);
			String token = getToken(user.getId(), user.getEmail(), authorities);
			authenticated.setDetails(new ResponseToken(token, new MemberDto(user)));
			return authenticated;
		} catch (IllegalArgumentException e) {
			throw new BadCredentialsException(e.getMessage());
		} catch (DataAccessException e) {
			throw new AuthenticationServiceException(e.getMessage(), e);
		}
	}
	
	private String getToken(Long id, String email, List<GrantedAuthority> authorities) {
		String[] roles = authorities.stream()
			.map(GrantedAuthority::getAuthority)
			.toArray(String[]::new);
		return jwt.sign(Jwt.Claims.from(id, email, roles));
	}
	
}