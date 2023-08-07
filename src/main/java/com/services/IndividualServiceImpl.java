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
		if(registerPayload.getIdNumber().length() != 11) {
			throw new CustomException("ID number must be length 11.");
		}
		
		// Encode id number
		BCryptPasswordEncoder idEncoder = new BCryptPasswordEncoder();
        String encryptedId = idEncoder.encode(registerPayload.getIdNumber());
        
        Individual newUser = new Individual(registerPayload);
        newUser.setIdNumber(encryptedId);
        
        Individual createdUser = userRepo.insert(newUser);
        log.info("Registered User: " + createdUser.toString());

        return createdUser;
 
		
	}
	
	public Individual loginUser(IndividualLoginPayload loginPayload) throws CustomException {
		
		// Encrypt the input idNumber
	    BCryptPasswordEncoder idEncoder = new BCryptPasswordEncoder();
	    String encryptedIdNumber = idEncoder.encode(loginPayload.getIdNumber());

	    log.info(encryptedIdNumber);
		
		
		Individual loginedUser = userRepo.findByIdNumber(encryptedIdNumber);
		if (loginedUser == null) {
			log.info("ID YOKKKKKKKK");
            throw new CustomException("ID number does not exist.");
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
