package com.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.models.Institution;
import com.models.Individual;
import com.payload.IndividualLoginPayload;
import com.payload.IndividualRegisterPayload;
import com.payload.InstitutionLoginPayload;
import com.payload.InstitutionRegisterPayload;
import com.services.IndividualService;
import com.services.InstitutionService;

@RestController
@RequestMapping("/auth")
public class AuthController {
		private static final Logger log = LoggerFactory.getLogger(ReservationController.class);
	
		@Autowired
		private InstitutionService institutionService;
		
		@Autowired
		private IndividualService individualService;
	
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
				Individual loginedUser = individualService.loginUser(loginUser);
				log.info("user LOGGED IN");
				return ResponseEntity.ok(loginedUser);
				
			} catch (CustomException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
			}
		}
		
		@PostMapping("/institution/login")
		public ResponseEntity<?> loginInstitution(@RequestBody InstitutionLoginPayload loginUser) {
			try {
				Institution loginedUser = institutionService.loginUser(loginUser);
				log.info("user LOGGED IN");
				return ResponseEntity.ok(loginedUser);
				
			} catch (CustomException e) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
			}
		}
		
		// return desired profile
		@GetMapping("/institutionUser/{profileId}")
		public ResponseEntity<?> getInstProfileById(@PathVariable String profileId) {
			Institution user = institutionService.getProfileById(profileId);
			log.info("institution RETURNED");
			return ResponseEntity.ok(user);
		}
		
		// return desired profile
		@GetMapping("/individualUser/{profileId}")
		public ResponseEntity<?> getIndProfileById(@PathVariable String profileId) {
			Individual user = individualService.getProfileById(profileId);
			log.info("Individual RETURNED");
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

