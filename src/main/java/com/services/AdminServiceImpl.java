package com.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.models.Admin;
import com.models.Day;
import com.models.Individual;
import com.models.Institution;
import com.models.Reservation;
import com.models.TimeSlot;
import com.payload.AdminPayload;
import com.payload.IndividualLoginPayload;
import com.payload.IndividualRegisterPayload;
import com.repo.AdminRepo;
import com.repo.DayRepo;
import com.repo.IndividualRepo;
import com.repo.InstitutionRepo;
import com.repo.ReservationRepo;
import com.services.ReservationServiceImpl.CustomException;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepo adminRepo;
	
	@Autowired
	 private InstitutionRepo institutionRepo;
	
	 @Autowired
	 private ReservationRepo reservationRepository;
	 
	 @Autowired
	 private DayRepo dayRepo;
	 
	 @Autowired
	 private DayServiceImpl dayService;
	 
	 @Autowired
	 private IndividualRepo individualRepo;
	
	private static final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	// create new admin
	public Admin createAdmin(AdminPayload admin) throws CustomException {
		
		
		Admin repoAdmin = adminRepo.findByUsername(admin.getUsername());
		if(repoAdmin != null) {
			throw new CustomException("Username already exists.");
		}
		
		// Encrypt the user's password before saving
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encryptedPassword = passwordEncoder.encode(admin.getPassword());
        
        Admin newAdmin = new Admin();
        newAdmin.setPassword(encryptedPassword);
        newAdmin.setUsername(admin.getUsername());
        newAdmin.setRole("ROLE_ADMIN");
        
        Admin createdAdmin = adminRepo.insert(newAdmin);
        log.info("Registered User: " + createdAdmin.toString());

        return createdAdmin;	
		
	}
	
	public Admin loginAdmin(AdminPayload loginAdmin) throws CustomException{
		
		Admin repoAdmin = adminRepo.findByUsername(loginAdmin.getUsername());
		
		if(repoAdmin == null || !matchesPassword(loginAdmin.getPassword(), repoAdmin.getPassword())){
			throw new CustomException("Username or password is wrong");
		}

		log.info("Loginned Admin: " + repoAdmin.toString());
        return repoAdmin;
	
	}
	
	
	public Admin getProfileById(String profileId) throws CustomException{
		return adminRepo.findById(profileId)
	             .orElseThrow(() -> new CustomException("Profile not found"));

	}
	
	// delete any reservation
	public void deleteReservation(String reservationId) {
		 // Find the reservation to be deleted
		
		log.info("RESERVASYON BULMA ERROR 1");

	    Reservation reservationToDelete = reservationRepository.findById(reservationId)
	            .orElseThrow(() -> new CustomException("Reservation not found"));
	    log.info("reservasyon: " + reservationToDelete.toString());
	    log.info("RESERVASYON BULMA ERROR 2");

	    log.info("DATE BULMA ERROR 1");
	    // Get the date of the reservation to update the corresponding Day object
	    long reservationDate = reservationToDelete.getRequestDateTime();
	    
	    //long dateSinceEpoch = reservationDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();
	    
	    Instant instant = Instant.ofEpochMilli(reservationDate);
	    ZonedDateTime zonedDateTime = instant.atZone(ZoneOffset.UTC);

        // Extract the LocalDate from the ZonedDateTime
        LocalDate localDate = zonedDateTime.toLocalDate();
        
        long milliReservationDate = localDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();

	    String strLong = "" + milliReservationDate;
	    log.info(strLong);
		 
	    log.info("DATE BULMA ERROR 2");
	    
	    log.info(reservationToDelete.getId());
	    
	    
	    
	    List<Individual> allIndividuals = individualRepo.findAll();
	    List<Institution> allInstitutions = institutionRepo.findAll();

	    // Step 3: Remove reservation from users and institutions
	    for (Individual individual : allIndividuals) {
	        if (individual.getReservations().removeIf(reservation -> reservation.getId().equals(reservationId))) {
	            individualRepo.save(individual);
	        }
	    }

	    for (Institution institution : allInstitutions) {
	        if (institution.getReservations().removeIf(reservation -> reservation.getId().equals(reservationId))) {
	            institutionRepo.save(institution);
	        }
	    }
	    
	 // Find the Day object for the reservation date
	    Day day = dayRepo.findByDate(milliReservationDate);
	    
	    log.info("error burda!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	    if (day == null) {
	        throw new CustomException("Day not found for the reservation date.");
	    }
	    log.info("REPODA BUL ERROR 2");
	    
	    log.info("KAPASITE UPDATE ERROR 1");
	 // Update the capacity for the reservation time slot
	    int freedCapacity = reservationToDelete.getVisitorCount();
	    long reservationTime = reservationToDelete.getRequestDateTime();
	    TimeSlot timeSlot = day.getTimeSlotsAvailability().get(reservationTime);
	    log.info("KAPASITE UPDATE ERROR 2");
	    if (timeSlot != null) {
	        int currentCapacity = timeSlot.getCapacity();
	        timeSlot.setCapacity(currentCapacity + freedCapacity);
	        timeSlot.setAvailable(true);

	        // Save the updated Day object back to MongoDB
	        dayRepo.save(day);

	        // Delete the reservation after updating the capacity
	        reservationRepository.delete(reservationToDelete);
	    } else {
	        throw new CustomException("Time slot not found for the reservation time.");
	    }
	    
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
