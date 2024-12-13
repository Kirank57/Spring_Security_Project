package com.kiran.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kiran.serviceimp.UserSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserSuccessHandler userSuccessHandler;

	@Autowired
	private UserDetailsService userDetailsService;


	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder());

		return provider;
	}





	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		  http
          .csrf().disable() 
          .authorizeRequests()
              .requestMatchers("/login", "/logout","/","/register","/submit").permitAll() 
              .requestMatchers("/admin","/viewlist").hasAuthority("ADMIN")  
              .requestMatchers("/user").hasAuthority("USER") 
              .anyRequest().authenticated()
          .and()
          .formLogin()
              .loginPage("/login") 
              .successHandler(userSuccessHandler).permitAll()
            
          .and()
          .logout()
              .logoutUrl("/logout") 
              .deleteCookies("JSESSIONID") 
              .invalidateHttpSession(true) 
              .clearAuthentication(true)
              .logoutSuccessUrl("/")
              .permitAll();

      return http.build();
       

	}
	
	@Bean
	 public PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	 }
	
	


}
