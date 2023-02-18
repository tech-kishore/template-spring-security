package com.kishore.securityauth101.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// authenticate every requests
		http.authorizeHttpRequests(
				auth -> 
					auth
//					.requestMatchers("/users/**").hasRole("USER") //check later
					.anyRequest().authenticated()
					);

		// disable session
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		// disable form login
		http.formLogin().disable();

		// enable basic authentication
		http.httpBasic();

		// disable csrf
		http.csrf().disable();

		// enable oauth2 resource sever for JWT
		http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
		
		// enable frame for h2-console(only for development purpose)
		http.headers().frameOptions().sameOrigin();

		return http.build();
	}

	

}
