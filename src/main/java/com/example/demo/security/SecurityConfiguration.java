package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
		return daoAuthenticationProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable();
		
		http.authorizeHttpRequests().requestMatchers("/css/**", "/js/**", "/images/**").permitAll();
		
		http.authorizeHttpRequests().requestMatchers("/trangchu").permitAll();
		http.authorizeHttpRequests().requestMatchers("/admin/api/*").permitAll();
		http.authorizeHttpRequests()
		.requestMatchers("/admin/**").hasAuthority("ADMIN")
		.requestMatchers("/dangky").permitAll()
		.requestMatchers("/saveUser").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin()
		.loginPage("/login")
		.defaultSuccessUrl("/trangchu")
		.failureUrl("/login?error=true")
		.permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/accessDenied");
		
		http.rememberMe().key("uniqueAndSecret").tokenValiditySeconds(60*60*1000);
		
		return http.build();
	}
}
