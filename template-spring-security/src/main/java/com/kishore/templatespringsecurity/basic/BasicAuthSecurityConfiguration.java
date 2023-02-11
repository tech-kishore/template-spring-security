package com.kishore.templatespringsecurity.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BasicAuthSecurityConfiguration{
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		//authenticate every requests
		http.authorizeHttpRequests(
				auth -> auth.anyRequest().authenticated()
				);
		
		//disable session
		http.sessionManagement(
				session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				);
		
		//disable form login
		http.formLogin().disable();
		
		//enable basic authentication
		http.httpBasic();
		
		//disable csrf
		http.csrf().disable();
		
		return http.build();
	}

}

