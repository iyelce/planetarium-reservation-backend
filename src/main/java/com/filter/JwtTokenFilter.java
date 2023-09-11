package com.filter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
//import com.services.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.controller.ReservationController;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.core.annotation.Order;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;



@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;
//    @Autowired
//    private AuthenticationManager authenticationManager;
    
    private static final Logger log = LoggerFactory.getLogger(JwtTokenFilter.class);
    

    public JwtTokenFilter(JwtTokenUtil jwtTokenUtil, UserDetailsService userDetailsService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

	@Override
	protected void doFilterInternal(
			jakarta.servlet.http.HttpServletRequest request,
			jakarta.servlet.http.HttpServletResponse response, jakarta.servlet.FilterChain filterChain)
			throws jakarta.servlet.ServletException, IOException {
			log.info("JWTTokenFilter in the doFilterInternal-------------");

			    
			final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
			
			log.info("request:  "+ request.getRequestURL());
		
			    
//		    // Check if it's a login request
			if (isLoginRequest(request)) {
				
				filterChain.doFilter(request, response);
				return;
				
				
				
				
//			    try {
//			    	log.info("login request");
//			       // Extract credentials from the request and authenticate the user
//			        Authentication authentication = attemptAuthentication(request);
//
//			        // If authentication is successful, generate a token
//			        if (authentication.isAuthenticated()) {
//			        	log.info("authenticated user");
//			            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//			            String token = jwtTokenUtil.generateToken(userDetails);
//
//			            UsernamePasswordAuthenticationToken authenticated =
//						        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//			            
//			            // Set the token in the response header
//			            response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
//			            
//
//			            // Set the security context with the authenticated user
//			            SecurityContextHolder.getContext().setAuthentication(authenticated);
//			            log.info("secity context holder in login: " + SecurityContextHolder.getContext().toString());
//			            
//			         }
//			    } catch (AuthenticationException e) {
//			        
//			    	log.info("not authenticated");
//			    	
//			        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//			        return;
//			    }
			}
//			else {
			    
				if (header == null || !header.startsWith("Bearer ")) {
				    log.info("JWT header null or Bearer missing");
				    filterChain.doFilter(request, response);
				    return;
				}
				final String token = header.split(" ")[1].trim();
				log.info("JWT--- " + token);
				    
				log.info("jwt filterda username   " + jwtTokenUtil.extractUsername(token));
				    
	
				UserDetails userDetails = userDetailsService
				    .loadUserByUsername(jwtTokenUtil.extractUsername(token));
	
				if (jwtTokenUtil.validateToken(token, userDetails)) {
				    log.info("Token is valid");
				        
				    UsernamePasswordAuthenticationToken authentication =
				        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				        
				    log.info("UsernamePasswordAuthenticationToken  " + authentication.toString());
				        
				    SecurityContextHolder.getContext().setAuthentication(authentication);
				} else {
				    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				    response.getWriter().write("Unauthorized: Invalid token");
				    return;
				}
	
				
//			}
			
			filterChain.doFilter(request, response);
			
		}

		private boolean isLoginRequest(HttpServletRequest request) {
			return request.getRequestURI().equals("/auth/institution/login") || 
			      request.getRequestURI().equals("/auth/individual/login") ||
			      request.getRequestURI().equals("/auth/admin/login");
		}
		
//		protected Authentication attemptAuthentication(HttpServletRequest request) {
//		    try {
//		        // Read the JSON payload from the request body
//		        ObjectMapper objectMapper = new ObjectMapper();
//		        UserCredentials userCredentials = objectMapper.readValue(request.getInputStream(), UserCredentials.class);
//
//		        // Now you can access the username and password from userCredentials object
//		        String username = userCredentials.getUsername();
//		        String password = userCredentials.getPassword();
//
//		        log.info(username + " " + password);
//		        
//		        Authentication authentication = authenticationManager.authenticate(
//		            new UsernamePasswordAuthenticationToken(username, password));
//
//		        return authentication;
//		    } catch (IOException e) {
//		        log.info("NERDE CREDENTIALSSSSS");
//		        return null;
//		    }
//		}
			
}
