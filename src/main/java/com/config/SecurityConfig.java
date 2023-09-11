package com.config;

import java.util.Arrays;

import org.slf4j.Logger;

import static org.springframework.security.config.Customizer.withDefaults;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
//import com.services.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.filter.JwtTokenFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private final UserDetailsService userDetailsService;
	@Autowired
	private JwtTokenFilter jwtTokenFilter;
	
	private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    public SecurityConfig(UserDetailsService userDetailsService, JwtTokenFilter jwtTokenFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenFilter = jwtTokenFilter;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
      return authConfig.getAuthenticationManager();
    }
    
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	log.info("!!!!!!!filter chain invoked!!!!!!!");
        http
                .authorizeHttpRequests(authorize -> authorize
                                .requestMatchers("/auth/institution/login",
                                        "/auth/individual/login",
                                        "/auth/institution/register",
                                        "/auth/individual/register",
                                        "/admin/login")
                                .permitAll()
                                .anyRequest().authenticated()

                )
                
                .csrf(AbstractHttpConfigurer::disable) //for nowwwwwwwww
                .anonymous(AbstractHttpConfigurer::disable);
                http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                http.addFilterAfter(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

                        
                        
    	log.info("!!!!!!!filter chain bitis!!!!!!!!");
        return http.build();
    }


}


