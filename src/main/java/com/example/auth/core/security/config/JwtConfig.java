package com.example.auth.core.security.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
	
	@NotNull
	private String header;
	@NotNull
	private String issuer;
	@NotNull
	private String clientSecret;
	
	private int expirySeconds;
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("header", header)
			.append("issuer", issuer)
			.append("clientSecret", clientSecret)
			.append("expirySeconds", expirySeconds)
			.toString();
	}
}
