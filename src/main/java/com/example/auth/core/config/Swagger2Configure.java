package com.example.auth.core.config;

import com.example.auth.core.config.pages.Pageable;
import com.example.auth.core.security.config.JwtConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static java.util.Collections.singleton;
import static java.util.Collections.singletonList;
import static springfox.documentation.builders.RequestHandlerSelectors.withMethodAnnotation;

@Configuration
@EnableSwagger2
public class Swagger2Configure {
	
	private final JwtConfig jwtConfig;
	
	public Swagger2Configure(JwtConfig jwtConfig) {
		this.jwtConfig = jwtConfig;
	}
	
	@Bean
	public Docket restApi() {
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(apiInfo())
			.ignoredParameterTypes(AuthenticationPrincipal.class, Pageable.class)
			.securitySchemes(singletonList(apiKey()))
			.securityContexts(singletonList(securityContext()))
			.produces(singleton("application/json"))
			.consumes(singleton("application/json"))
			.useDefaultResponseMessages(false)
			// endpoint 검색
			.select()
			.apis(withMethodAnnotation(ApiOperation.class))
			.build();
	}
	
	@Bean
	public SecurityConfiguration security() {
		return SecurityConfigurationBuilder.builder()
			.scopeSeparator(",")
			.additionalQueryStringParams(null)
			.useBasicAuthenticationWithAccessCodeGrant(false)
			.build();
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("auth-server")
			.contact(new Contact("seokrae", null, "seokrae@kakao.com"))
			.version("1.0.0")
			.build();
	}
	
	private ApiKey apiKey() {
		return new ApiKey("Authorization", jwtConfig.getHeader(), "header");
	}
	
	private SecurityContext securityContext() {
		return SecurityContext.builder()
			.securityReferences(securityReference())
			.operationSelector(o -> true)
			.build();
	}
	
	private List<SecurityReference> securityReference() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return singletonList(new SecurityReference("Authorization", authorizationScopes));
	}
	
}