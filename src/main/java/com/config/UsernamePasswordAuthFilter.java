//package com.config;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//
//import com.controller.ReservationController;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Component
//public class UsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {
//
//	@Autowired
//    private CustomAuthenticationProvider authenticationManager;
//    private static final Logger log = LoggerFactory.getLogger(UsernamePasswordAuthFilter.class);
//    
//    
//    
//    public UsernamePasswordAuthFilter(CustomAuthenticationProvider authenticationManager) {
//        log.info("USERNAME PASSWORD AUTH INVOKED");
//        this.authenticationManager = authenticationManager;
//        this.setFilterProcessesUrl("/auth/individual/login, /auth/institution/login, /admin/login"); // Specify the URL for login
//        log.info("constructor bittiiiiiii");
//    }
//
//
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
//        // Extract the username and password from the request
//    	log.info("ATTEMPTING AUTHHHHHHHH");
//        String username = obtainUsername(request);
//        String password = obtainPassword(request);
//
//        
//        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
//
//        // Authenticate the user
//        return authenticationManager.authenticate(authRequest);
//    }
//
//    protected void successfulAuthentication(
//            HttpServletRequest request, HttpServletResponse response, Authentication authResult) {
//        // You can customize the behavior when authentication is successful
//    	
//    	log.info("login succesfulllllllllllllllllllllllllllllllll");
//
//        // Set the Authentication object in the SecurityContextHolder
//        SecurityContextHolder.getContext().setAuthentication(authResult);
//
//        // For example, you can log the success
//        log.info("Authentication successful: " + authResult.getName());
//    }
//}
