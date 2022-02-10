/**
 *  ScBpartyServerApplication.java
 *
 *  Main class to control server 
 *
 *  Created by
 *  Thitiporn Sukpartcharoen 
 *
 *  6 Jan 2022
 */
package com.example.SCBpartyServer;

import java.util.List;

import com.example.SCBpartyServer.security.AuthorizationFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@SpringBootApplication
public class ScBpartyServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScBpartyServerApplication.class, args);
	}

	/**
     * This class is sercurity configuration that manage request with Authorization
	 * and CRUD api.
     */
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
				.cors().configurationSource(request -> {
				CorsConfiguration cors = new CorsConfiguration();
				cors.setAllowedOrigins(List.of("http://localhost:3000"));
				cors.setAllowedMethods(List.of("GET","POST", "DELETE"));
				cors.setAllowedHeaders(List.of("*"));
				return cors;
			  	})
			  	.and().csrf().disable()
				.addFilterAfter(new AuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, "/login").permitAll()
				.antMatchers(HttpMethod.POST, "/login/*").permitAll()
				.antMatchers(HttpMethod.POST, "/login/**").permitAll()
				.antMatchers(HttpMethod.POST, "/register").permitAll()
				.antMatchers(HttpMethod.POST, "/register/*").permitAll()
				.antMatchers(HttpMethod.POST, "/register/**").permitAll()
				.antMatchers(HttpMethod.GET, "/users").permitAll()
				.antMatchers(HttpMethod.DELETE, "/delete/users").permitAll()
				.antMatchers(HttpMethod.DELETE, "/delete/party").permitAll()
				.anyRequest().authenticated();
		}
		
	}

}

