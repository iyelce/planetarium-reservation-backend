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
import com.models.Institution;
import com.models.Reservation;
import com.payload.IndividualLoginPayload;
import com.payload.IndividualRegisterPayload;
import com.repo.IndividualRepo;
import com.repo.ReservationRepo;
import com.services.InstitutionServiceImpl.CustomException;

@Service
public class IndividualServiceImpl implements IndividualService{
	
	private static final Logger log = LoggerFactory.getLogger(ReservationServiceImpl.class);
    @Autowired private IndividualRepo userRepo;
    @Autowired private ReservationRepo reservationRepo;
	
	public Individual createUser(IndividualRegisterPayload registerPayload) throws CustomException{
		
		Individual repoUser = userRepo.findByUsername(registerPayload.getUsername());

        if (repoUser != null) {
            throw new CustomException("Username already exists.");
        }
        
		if(registerPayload.getIdNumber().length() != 11) {
			throw new CustomException("ID number must be length 11.");
		}
		
		
		// Encode id number
		BCryptPasswordEncoder idEncoder = new BCryptPasswordEncoder();
        String encryptedId = idEncoder.encode(registerPayload.getIdNumber());
        
     // Encrypt the user's password before saving
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(registerPayload.getPassword());
        
        Individual newUser = new Individual(registerPayload);
        newUser.setIdNumber(encryptedId);
        newUser.setPassword(encryptedPassword);
        
        Individual createdUser = userRepo.insert(newUser);
        log.info("Registered User: " + createdUser.toString());

        return createdUser;
 
		
	}
	
	public Individual loginUser(IndividualLoginPayload loginPayload) throws CustomException {
		
		Individual loginedUser = userRepo.findByUsername(loginPayload.getUsername());

        if (loginedUser == null || !matchesPassword(loginPayload.getPassword(), loginedUser.getPassword())) {
            throw new CustomException("Username or password is wrong");
        }
		
		log.info("Loginned Individual: " + loginedUser.toString());
        return loginedUser;
		
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
