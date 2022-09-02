package com.nayan.mybox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nayan.mybox.service.MongoUserService;


@EnableWebSecurity
@Configuration
public class SecurityConfiguration{
	
	@Autowired
	MongoUserService userService;
	
	
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userService);
	}


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        
    	http.authorizeHttpRequests()
    		.antMatchers("/signup").permitAll()	
        	.antMatchers("/dashboard").authenticated()
        	.antMatchers("/dashboard/upload/**").authenticated()
        	.antMatchers("/dashboard/download/**").permitAll()
        	.and().formLogin().defaultSuccessUrl("/dashboard");
        
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
    	return  NoOpPasswordEncoder.getInstance();
    }
}
