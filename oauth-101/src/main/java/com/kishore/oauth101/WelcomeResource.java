package com.kishore.oauth101;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeResource {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping({"/","welcome"})
	public String welcome(Authentication authentication) {
		logger.info(authentication.toString());
		return "Welcome to Oauth2 101 Authentication Template application!";
	}
}
