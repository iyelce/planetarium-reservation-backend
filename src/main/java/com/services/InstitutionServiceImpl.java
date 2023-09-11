package com.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.models.Reservation;
import com.models.Institution;
import com.payload.InstitutionLoginPayload;
import com.payload.InstitutionRegisterPayload;
import com.repo.ReservationRepo;
import com.repo.InstitutionRepo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class InstitutionServiceImpl implements InstitutionService {

	private static final Logger log = LoggerFactory.getLogger(InstitutionServiceImpl.class);
    @Autowired private InstitutionRepo userRepo;
    @Autowired private ReservationRepo reservationRepo;

    @Override
    public Institution createUser(InstitutionRegisterPayload registerPayload) throws CustomException {
        Institution repoUser = userRepo.findByUsername(registerPayload.getUsername());

        if (repoUser != null) {
            throw new CustomException("Username already exists.");
        }

        // Encrypt the user's password before saving
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(registerPayload.getPassword());

        Institution newUser = new Institution(registerPayload);
        newUser.setPassword(encryptedPassword);
        
     // Set the user's authorities
        newUser.setRole("ROLE_INSTITUTION");

        Institution createdUser = userRepo.insert(newUser);
        log.info("Registered User: " + createdUser.toString());

        return createdUser;
    }


    @Override
    public Institution loginUser(InstitutionLoginPayload loginUser) throws CustomException {
        Institution loginedUser = userRepo.findByUsername(loginUser.getUsername());

        if (loginedUser == null || !matchesPassword(loginUser.getPassword(), loginedUser.getPassword())) {
            throw new CustomException("Username or password is wrong");
        }

        log.info("Loginned Institution: " + loginedUser.toString());
        return loginedUser;
    }
    
    @Override
    public Institution getProfileById(String profileId) throws CustomException {
        return userRepo.findById(profileId)
                .orElseThrow(() -> new CustomException("Profile not found"));
    }

    @Override
    public List<Reservation> getProfileReservations(String profileId) throws CustomException {
    	Institution user = userRepo.findById(profileId)
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
