package com.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.models.Institution;
import com.filter.JwtTokenUtil;
import com.models.Individual;
import com.payload.IndividualLoginPayload;
import com.payload.IndividualRegisterPayload;
import com.payload.InstitutionLoginPayload;
import com.payload.InstitutionRegisterPayload;
import com.services.IndividualService;
import com.services.InstitutionService;
//import com.services.UserDetailsService;

@RestController
@RequestMapping("/auth")
public class AuthController {
		private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	
		@Autowired
		private InstitutionService institutionService;
		
		@Autowired
		private IndividualService individualService;
		
		@Autowired
	    private AuthenticationManager authenticationManager;

	    @Autowired
	    private JwtTokenUtil jwtTokenUtil;
	    
	    @Autowired 
	    private UserDetailsService userDetailService;
	
		@PostMapping("/institution/register")
		public ResponseEntity<?> createInstitution(@RequestBody InstitutionRegisterPayload registerPayload) {
			try {
				Institution createdUser = institutionService.createUser(registerPayload);
				log.info("institution CREATED");
				return ResponseEntity.ok(createdUser);
			} catch (CustomException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}
		}
		
		@PostMapping("/individual/register")
		public ResponseEntity<?> createIndividual(@RequestBody IndividualRegisterPayload registerPayload) {
			try {
				log.info("ind regis reqqqqqqqqqqqqqqqqqqqq");
				Individual createdUser = individualService.createUser(registerPayload);
				log.info("individual CREATED");
				return ResponseEntity.ok(createdUser);
			} catch (CustomException e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
			}
		}
		
		@PostMapping("/individual/login")
		public ResponseEntity<?> loginIndividual(@RequestBody IndividualLoginPayload loginUser) {
			try {
	            // Authenticate the individual user and generate a token
	            //authenticateUser(loginUser.getUsername(), loginUser.getPassword(), "individual");
				log.info("Auth    username: "+ loginUser.getUsername()+ "password: "+ loginUser.getPassword());
				Authentication authentication =
	                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				String ind = authentication.getName();
	            	          
	            UserDetails userDetails = userDetailService.loadUserByUsername(ind);
	            String token = jwtTokenUtil.generateToken(userDetails);
	            log.info(token);
	            log.info("TOKEN OLUSTU");
	            return ResponseEntity.ok(Map.of("token", token));
	        } catch (AuthenticationException e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	        }
	    }
		
		@PostMapping("/institution/login")
		public ResponseEntity<?> loginInstitution(@RequestBody InstitutionLoginPayload loginUser) {
			log.info("in the controller");
			try {
				
	            log.info("Auth    username: "+ loginUser.getUsername()+ "password: "+ loginUser.getPassword());
	            Authentication authentication =
	                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	            String inst = authentication.getName();
	            log.info("Auth name: " + inst);
	            
	            UserDetails userDetails = userDetailService.loadUserByUsername(inst);
	            log.info("USER CEKILDI");
	            String token = jwtTokenUtil.generateToken(userDetails);
	            log.info(token);
	            log.info("TOKEN OLUSTU");
	            return ResponseEntity.ok(Map.of("token", token));
	        } catch (AuthenticationException e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	        }
		}
		
		// return desired profile
		@PreAuthorize("hasAuthority('ROLE_INSTITUTION')")
		@GetMapping("/institution/{profileId}")
		public ResponseEntity<?> getInstProfileById(@PathVariable String profileId) {
			Institution inst = institutionService.getProfileById(profileId);
			UserDetails user = userDetailService.loadUserByUsername(inst.getUsername());
			
			log.info(user.getAuthorities().toString());
						
			log.info("institution RETURNED");
			// user details olarak donduruyo, degistirilebilir
			return ResponseEntity.ok(user);
		}
		
		// return desired profile
		@PreAuthorize("hasAuthority('ROLE_INDIVIDUAL')")
		@GetMapping("/individual/{profileId}")
		public ResponseEntity<?> getIndProfileById(@PathVariable String profileId) {
			
			Individual ind = individualService.getProfileById(profileId);
			//authenticateUser(user.getUsername(), user.getPassword(), "individual");
			UserDetails user = userDetailService.loadUserByUsername(ind.getUsername());
			log.info("Individual RETURNED");
			
			// user details olarak donduruyo, degistirilebilir
			return ResponseEntity.ok(user);
		}

		
		@ExceptionHandler(CustomException.class)
		public ResponseEntity<?> handleUserNotFoundException(CustomException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	
		//Errors
		@ResponseStatus(HttpStatus.BAD_REQUEST)
		public class CustomException extends RuntimeException {
		    private String message;
	
		    public CustomException(String message) {
		        this.message = message;
		    }
	
		    public String getMessage() {
			       return message;
		    }
		}
		
	
}

