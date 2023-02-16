package com.kishore.securityauth101.security.auth.jwt;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
public class JwtAuthSecurityConfiguration {
	
	// TODO - Make it database driven
//	@Bean
//	public UserDetailsService userDetailsService() {
//		var user = User.withUsername("kishore")
//				.password("{noop}dummy")
//			//	.passwordEncoder(str -> passEncoder().encode(str))
//				.roles("ADMIN", "USER")
//				.build();
//
//		return new InMemoryUserDetailsManager(user);
//	}
	
	@Bean
	public DataSource dataSource() {
		return new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.H2)
				.addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
				.build();
	}
	
	@Bean
	public UserDetailsService userDetailsService(DataSource dataSource) {
		var user = User.withUsername("kishore")
				.password("dummy")
				.passwordEncoder(str -> passEncoder().encode(str))
				.roles("USER")
				.build();
		
		var admin = User.withUsername("admin")
				.password("dummy")
				.passwordEncoder(str -> passEncoder().encode(str))
				.roles("ADMIN")
				.build();
		
		var jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.createUser(user);
		jdbcUserDetailsManager.createUser(admin);

		return jdbcUserDetailsManager;
	}

	@Bean
	public BCryptPasswordEncoder passEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	// create RSA Key Pair
	@Bean
	public KeyPair keyPair() {
		try {
			 var keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			 keyPairGenerator.initialize(2048);
			 return keyPairGenerator.generateKeyPair();
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	// create RSA Key Object
	@Bean
	public RSAKey rsaKey(KeyPair keyPair) {
		return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
				.privateKey(keyPair.getPrivate())
				.keyID(UUID.randomUUID().toString())
				.build();
	}
	
	
	// create JwtDecoder
	@Bean
	public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
		return NimbusJwtDecoder
				.withPublicKey(rsaKey.toRSAPublicKey())
				.build();
	}
	
	// create JWKSource
	@Bean
	public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
		var jwkSet = new JWKSet(rsaKey);
		return (jwkSource,context) -> jwkSource.select(jwkSet);
	}

	// create JwtEncoder
	@Bean
	public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource){
		return new NimbusJwtEncoder(jwkSource);
	}
	

}
