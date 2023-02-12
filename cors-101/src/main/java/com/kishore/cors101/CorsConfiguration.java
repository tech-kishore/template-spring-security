package com.kishore.cors101;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/* *
 * CORS Configuration is separately done independently of spring security.
 * No dependency required to be added!
 * */
@Configuration
public class CorsConfiguration {

	@Bean
	public WebMvcConfigurer corsConfigutation() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**") // apply to all urls
						.allowedMethods("*") // apply to all methods
						.allowedOrigins("http://localhost:3000"); // allow requests only from this domain(origin)
			}
		};
	}

}
