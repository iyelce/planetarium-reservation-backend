package com.services;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.models.Individual;
import com.models.Reservation;
import com.payload.IndividualLoginPayload;
import com.payload.IndividualRegisterPayload;
import com.repo.IndividualRepo;

@Service
public class IndividualServiceImpl implements IndividualService {
	
	private static final Logger log = LoggerFactory.getLogger(IndividualServiceImpl.class);
    @Autowired private IndividualRepo userRepo;
    
	
    // birey olustur
    @Override
	public Individual createUser(IndividualRegisterPayload registerPayload) throws CustomException{
		
		Individual repoUser = userRepo.findByUsername(registerPayload.getUsername());

        if (repoUser != null) {
            throw new CustomException("Username already exists.");
        }

        
     // Encrypt the user's password before saving
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(registerPayload.getPassword());
        
        Individual newUser = new Individual(registerPayload);
        newUser.setPassword(encryptedPassword);
        
     // Set the user's authorities
        newUser.setRole("ROLE_INDIVIDUAL");
        
        Individual createdUser = userRepo.insert(newUser);
        log.info("Registered User: " + createdUser.toString());

        return createdUser;
 
		
	}
	
	// login birey
	@Override
    public Individual loginUser(IndividualLoginPayload loginPayload) throws CustomException {
        Individual userDetails = userRepo.findByUsername(loginPayload.getUsername());

        if (!matchesPassword(loginPayload.getPassword(), userDetails.getPassword())) {
            throw new CustomException("Username or password is wrong");
        }

        log.info("Loginned Individual: " + userDetails.getUsername());
        return userRepo.findByUsername(loginPayload.getUsername());
    }
	
	
	 public Individual getProfileById(String profileId) throws CustomException {
	     return userRepo.findById(profileId)
	             .orElseThrow(() -> new CustomException("Profile not found"));
	 }

	 public List<Reservation> getProfileReservations(String profileId) throws CustomException {
		 Individual user = userRepo.findById(profileId)
	                .orElseThrow(() -> new CustomException("Profile not found"));
	     return user.getReservations();
	 }
	 
	 private boolean matchesPassword(String rawPassword, String encodedPassword) {
	        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	        return passwordEncoder.matches(rawPassword, encodedPassword);
	    }
	
	
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
