package com.example.demo.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return
			http.authorizeHttpRequests(authorize ->
				authorize
					.requestMatchers("/webjars/**", "/css/**", "/js/**").permitAll()
					.requestMatchers("/microBlog/user/login", "/microBlog/admin/user/logout", "/microBlog/user/create", "/error").permitAll()
					.anyRequest().authenticated()
			)

			.formLogin(form ->
					form
					.loginProcessingUrl("/microBlog/user/login")
					.loginPage("/microBlog/user/login")
					.defaultSuccessUrl("/microBlog/admin/tweet/talk")
					.failureUrl("/microBlog/user/login?error")
			)
			.logout(logout ->
				logout
					.logoutUrl("/microBlog/admin/user/logout")
					.logoutSuccessUrl("/microBlog/user/login?logout")
					.deleteCookies("JSESSIONID")
			)
			.exceptionHandling(exceptions ->
				exceptions
					.accessDeniedPage("/403")
			)
			.csrf(csrf -> csrf.disable())
			.build();
	}

	@Bean
	AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	auth
		.userDetailsService(new UserDetailsServiceImpl())
		.passwordEncoder(passwordEncoder());
	}
}

