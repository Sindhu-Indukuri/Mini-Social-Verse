
package com.socialmedia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/api/users/**", "/api/posts/**", "/api/comments/**", "/uploads/**").permitAll()
	            .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
	            .anyRequest().authenticated()
	        )
	        .httpBasic(withDefaults());

	    return http.build();
	}

}
