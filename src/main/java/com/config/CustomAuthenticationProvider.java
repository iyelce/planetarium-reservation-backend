//package com.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.stereotype.Component;
////import com.services.UserDetailsService;
//
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    private final DaoAuthenticationProvider daoAuthenticationProvider;
//    private static final Logger log = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
//
//    public CustomAuthenticationProvider(UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
//    	log.info("IN THE CUSTOM AUTH");
//        this.daoAuthenticationProvider = new DaoAuthenticationProvider();
//        this.daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//        this.daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
//    }
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        log.info("authenticating in the custom authent");
//        return daoAuthenticationProvider.authenticate(authentication);
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
//
//
