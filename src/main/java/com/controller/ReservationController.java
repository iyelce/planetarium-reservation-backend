package com.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.models.Reservation;
import com.models.Individual;
import com.models.Institution;
import com.payload.ReservationPayload;
import com.repo.IndividualRepo;
import com.repo.InstitutionRepo;
import com.services.ReservationService;

@RestController
@RequestMapping("/reservation")
public class ReservationController {
	
		@Autowired private ReservationService reservationService;
		@Autowired private IndividualRepo individualRepo;
		@Autowired private InstitutionRepo institutionRepo;
		
		
		private static final Logger log = LoggerFactory.getLogger(ReservationController.class);

		
		// kurum icin rezervasyon yapma
		@PreAuthorize("hasAuthority('ROLE_INSTITUTION')")
		@PostMapping("/institution/{profileId}")
	    public ResponseEntity<?> createInstitutionReservation(@RequestBody ReservationPayload reservationPayload, @PathVariable String profileId) {
	        try {
	        	log.info("controllerdayızzzzzzz  " + reservationPayload.toString());
	        	log.info(profileId);
	            Reservation savedReservation = reservationService.createInstitutionReservation(reservationPayload, profileId);
	            log.info("inst reserv CREATED");
	            return ResponseEntity.ok(savedReservation);
	        } catch (CustomException e) {
	        	log.info("exception cought!!!!!!");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        }
	    }
		
		// birey icin rezervasyon yapma
		@PreAuthorize("hasAuthority('ROLE_INDIVIDUAL')")
		@PostMapping("/individual/{profileId}")
	    public ResponseEntity<?> createIndividualReservation(@RequestBody ReservationPayload reservationPayload, @PathVariable String profileId) {
	        try {
	        	log.info(reservationPayload.toString());
	        	log.info(profileId);
	            Reservation savedReservation = reservationService.createIndividualReservation(reservationPayload, profileId);
	            log.info("ind reserv CREATED");
	            return ResponseEntity.ok(savedReservation);
	        } catch (CustomException e) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	        }
	    }
		
		// kurumun yaptigi rezervasyonlari dondur
		@PreAuthorize("hasAuthority('ROLE_INSTITUTION')")
		@GetMapping("/institution/created/{profileId}")
	    public ResponseEntity<?> getInstitutionReservations(@PathVariable String profileId) {
	        
	        Institution user = institutionRepo.findById(profileId)
	                .orElseThrow(() -> new CustomException("Profile not found"));

	        List<Reservation> reservations = user.getReservations();

	        log.info("Profile reservations returned for user: " + user.getUsername());
	        return ResponseEntity.ok(reservations);
	    }
		
		// bireyin yaptigi rezervasyonlari dondur
		@PreAuthorize("hasAuthority('ROLE_INDIVIDUAL')")
		@GetMapping("/individual/created/{profileId}")
	    public ResponseEntity<?> getIndividualReservations(@PathVariable String profileId) {
	        Individual user = individualRepo.findById(profileId)
	                .orElseThrow(() -> new CustomException("Profile not found"));

	        List<Reservation> reservations = user.getReservations();

	        log.info("Profile reservations returned for user: " + user.getName() + " " + user.getSurname());
	        return ResponseEntity.ok(reservations);
	    }
		

		// rezervasyon silme (kurum ve birey icin ayni endpoint)
		@PreAuthorize("hasAuthority('ROLE_INDIVIDUAL') or hasAuthority('ROLE_INSTITUTION')")
		@DeleteMapping("/profile/{profileId}/reservation/{reservationId}")
	    public ResponseEntity<?> deleteReservation(@PathVariable String reservationId, @PathVariable String profileId) {
	        reservationService.deleteReservation(reservationId, profileId);
	        log.info("reserv DELETED");
	        return ResponseEntity.ok().body("Deleted");
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
		
		@ExceptionHandler(CustomException.class)
		public ResponseEntity<?> handleUserNotFoundException(CustomException ex) {
		    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	
}
