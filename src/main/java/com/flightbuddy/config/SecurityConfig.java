package com.flightbuddy.config;

import static com.flightbuddy.user.UserRole.ROLE_ADMIN;
import static com.flightbuddy.user.UserRole.ROLE_USER;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.flightbuddy.user.JWTFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
		
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/register.html", "/register", "/user/register").hasAuthority(ROLE_ADMIN.name())
	      	.antMatchers("/schedule.html", "/schedule", "/search/schedule/save").hasAnyAuthority(ROLE_ADMIN.name(), ROLE_USER.name())
	      	.antMatchers("/**").permitAll()
			.and()
				.addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class)
					.antMatcher("/register.html").antMatcher("/register").antMatcher("/user/register")
					.antMatcher("/schedule.html").antMatcher("/schedule").antMatcher("/search/schedule/save")
				.httpBasic()
			.and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
				.csrf()
		        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}
}
