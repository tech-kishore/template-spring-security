package com.kishore.oauth101;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeResource {
	@GetMapping({"/","welcome"})
	public String welcome() {
		return "Welcome to Oauth2 101 Authentication Template application!";
	}
}
