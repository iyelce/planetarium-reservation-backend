package com.services;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.models.Day;
import com.models.Individual;
import com.models.Reservation;
import com.models.TimeSlot;
import com.models.Institution;
import com.payload.ReservationPayload;
import com.repo.DayRepo;
import com.repo.IndividualRepo;
import com.repo.ReservationRepo;
import com.repo.InstitutionRepo;

import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.util.List;

@Service
public class ReservationServiceImpl implements ReservationService {
	
	 private static final Logger log = LoggerFactory.getLogger(ReservationServiceImpl.class);
	
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
	 
	 
	 
	 //TODO: change later!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	 int defaultCapacityValue = 250; 
	
	 
	 @Override
	 public Reservation createInstitutionReservation(ReservationPayload reservationPayload, String profileId) {
		 int visitorCount = reservationPayload.getVisitorCount();
		 LocalDate reservationDate = reservationPayload.getRequestDate();
		 LocalTime reservationTime = reservationPayload.getRequestTime();

		// Combine LocalDate and LocalTime to create a LocalDateTime
		 LocalDateTime localDateTime = reservationDate.atTime(reservationTime);

		 // Convert LocalDateTime to ZonedDateTime with a specific ZoneId (e.g., UTC)
		 ZoneId zoneId = ZoneId.of("UTC"); // Use the desired ZoneId here
		 ZonedDateTime reservationDateTime = ZonedDateTime.of(localDateTime, zoneId);

		 // Convert ZonedDateTime to milliseconds since the epoch (UTC)
		 long timeSinceEpoch = reservationDateTime.toInstant().toEpochMilli();
		 long dateSinceEpoch = reservationDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();

	     // Find or create the Day object for the requested date
	     log.info("reservation's date: " + reservationDate.toString());

	     
	     String strLong = "" + dateSinceEpoch;
		 log.info(strLong);
	     
		 
		 String strLongTime = "" + timeSinceEpoch;
		 log.info("long time for slots: " + strLongTime);
		 
		 
	     log.info("DAY BULMA ERROR!!!!!");
	     Day day = dayRepo.findByDate(dateSinceEpoch);
	     log.info("ERROR YOK!!!!!");

	     if (day == null) {
	         // The Day object for the requested date doesn't exist, create it using the DayServiceImpl
	         day = dayService.createDayFromReservation(reservationPayload, defaultCapacityValue);
	         log.info("Saved Day: " + day.toString());
	     } else {
	         log.info("existing day: " + day.toString());
	     }
	     
	     // Check if the requested time slot is available and has sufficient capacity
	     TimeSlot timeSlot = day.getTimeSlotsAvailability().get(timeSinceEpoch);
	     log.info("Time slot: {}", timeSlot.toString());
	     
	     log.info("TIME SLOT NULL MUUU MUSAIT MIIIIII ERROR 1");
	     if (timeSlot == null || !timeSlot.isAvailable()) {
	         throw new CustomException("The requested time slot is not available for reservations.");
	     }
	     
	     log.info("TIME SLOT NULL MUUU MUSAIT MIIIIII ERROR 2");

	     // check whether the requested number exceeds capacity
	     if ((timeSlot.getCapacity() - visitorCount) < 0) {
	    	 log.info("KAPASITE ASILDI");
	         throw new CustomException("Requested number exceeds the capacity of the time slot.");
	     }
	     
	     int remainingCapacity = timeSlot.getCapacity() - visitorCount;
	     log.info("visitors: {} --- remaining capacity: {}", visitorCount, remainingCapacity);
	     
	     timeSlot.setCapacity(remainingCapacity);

	     
	     log.info("GET VISITOR COUNT ERRORRRRR 1");
	     
	     // Perform validation on the reservation data
	     if (reservationPayload.getVisitorCount() <= 0) {
	         throw new CustomException("Visitor count must be greater than 0.");
	     }
	     log.info("GET VISITOR COUNT ERRORRRRR 2");
	     
	     // Convert ReservationPayload to Reservation entity
	     Reservation reservation = new Reservation(
	             reservationPayload.getVisitorCount(),
	             timeSinceEpoch,
	             true);

	     log.info("INSTITUTION BULMA ERROR");
	     Institution profile = institutionRepo.findById(profileId)
	             .orElseThrow(() -> new CustomException("Profile not found"));
	     log.info("BULDUUUUU");
	     
	     

	     log.info("RESERV SAVELEME ERROR 1");
	     Reservation savedReservation = reservationRepository.insert(reservation);

	     log.info("RESERV SAVELEME ERROR 2");
	     
	     log.info("Saved Reservation: " + savedReservation.toString());

	     reservation.setId(savedReservation.getId());
	     
	     log.info("REZERVASYONA EKLEME ERROR");
	     profile.getReservations().add(reservation);
	     log.info("ERROR YOK");
	     
	     
	     institutionRepo.save(profile);
	     log.info("user saveleme");
	     
	     
	     day.setDate(dateSinceEpoch);
	     log.info("date setleme");
	     
	     
	     dayRepo.save(day);
	     log.info("day saveleme");
	     
	     
	    
	     return savedReservation;
	 }
	 
	 
	 @Override
	 public Reservation createIndividualReservation(ReservationPayload reservationPayload, String profileId) {
		 int visitorCount = reservationPayload.getVisitorCount();
		 LocalDate reservationDate = reservationPayload.getRequestDate();
		 LocalTime reservationTime = reservationPayload.getRequestTime();

		// Combine LocalDate and LocalTime to create a LocalDateTime
		 LocalDateTime localDateTime = reservationDate.atTime(reservationTime);

		 // Convert LocalDateTime to ZonedDateTime with a specific ZoneId (e.g., UTC)
		 ZoneId zoneId = ZoneId.of("UTC"); // Use the desired ZoneId here
		 ZonedDateTime reservationDateTime = ZonedDateTime.of(localDateTime, zoneId);

		 // Convert ZonedDateTime to milliseconds since the epoch (UTC)
		 long timeSinceEpoch = reservationDateTime.toInstant().toEpochMilli();
		 long dateSinceEpoch = reservationDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();

	     // Find or create the Day object for the requested date
	     log.info("reservation's date: " + reservationDate.toString());

	     
	     String strLong = "" + dateSinceEpoch;
		 log.info(strLong);
	     
		 
		 String strLongTime = "" + timeSinceEpoch;
		 log.info("long time for slots: " + strLongTime);
		 
		 
	     log.info("DAY BULMA ERROR!!!!!");
	     Day day = dayRepo.findByDate(dateSinceEpoch);
	     log.info("ERROR YOK!!!!!");

	     if (day == null) {
	         // The Day object for the requested date doesn't exist, create it using the DayServiceImpl
	         day = dayService.createDayFromReservation(reservationPayload, defaultCapacityValue);
	         log.info("Saved Day: " + day.toString());
	     } else {
	         log.info("existing day: " + day.toString());
	     }
	     
	     // Check if the requested time slot is available and has sufficient capacity
	     TimeSlot timeSlot = day.getTimeSlotsAvailability().get(timeSinceEpoch);
	     log.info("Time slot: {}", timeSlot.toString());
	     
	     log.info("TIME SLOT NULL MUUU MUSAIT MIIIIII ERROR 1");
	     if (timeSlot == null || !timeSlot.isAvailable()) {
	         throw new CustomException("The requested time slot is not available for reservations.");
	     }
	     
	     log.info("TIME SLOT NULL MUUU MUSAIT MIIIIII ERROR 2");

	     // check whether the requested number exceeds capacity
	     if ((timeSlot.getCapacity() - visitorCount) < 0) {
	    	 log.info("KAPASITE ASILDI");
	         throw new CustomException("Requested number exceeds the capacity of the time slot.");
	     }
	     
	     int remainingCapacity = timeSlot.getCapacity() - visitorCount;
	     log.info("visitors: {} --- remaining capacity: {}", visitorCount, remainingCapacity);
	     
	     timeSlot.setCapacity(remainingCapacity);
	     
	     log.info("GET VISITOR COUNT ERRORRRRR 1");
	     
	     // Perform validation on the reservation data
	     if (reservationPayload.getVisitorCount() <= 0) {
	         throw new CustomException("Visitor count must be greater than 0.");
	     }
	     log.info("GET VISITOR COUNT ERRORRRRR 2");
	     
	     // Convert ReservationPayload to Reservation entity
	     Reservation reservation = new Reservation(
	             reservationPayload.getVisitorCount(),
	             timeSinceEpoch,
	             true);
	     
	     Individual profile = individualRepo.findById(profileId)
	             .orElseThrow(() -> new CustomException("Profile not found"));
	     
	     for (Reservation reserv : profile.getReservations()) {
	    	    log.info("Reservation: " + reserv.toString());
	    	}
	     log.info("noluyoooooooooooooooo");
	     
	     

	     log.info("RESERV SAVELEME ERROR 1");
	     Reservation savedReservation = reservationRepository.insert(reservation);

	     log.info("RESERV SAVELEME ERROR 2");
	     
	     log.info("Saved Reservation: " + savedReservation.toString());
	     
	     reservation.setId(savedReservation.getId());
	     
	     profile.getReservations().add(reservation);
	     for (Reservation reserv : profile.getReservations()) {
	    	    log.info("Reservation: " + reserv.toString());
	    	}
	     individualRepo.save(profile);
	     day.setDate(dateSinceEpoch);
	     dayRepo.save(day);

	     return savedReservation;
	 }
 
		
		@Override
		public void deleteReservation(String reservationId, String profileId) {
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
		    
		    
		    Optional<Individual> userInd = individualRepo.findById(profileId);
		    if(userInd.isPresent()) {
		    	log.info("hello??????");
		    	Individual ind = userInd.get();
		    	List<Reservation> updatedReserv = ind.getReservations();
				updatedReserv.remove(reservationToDelete);
				ind.setReservations(updatedReserv);
		    	individualRepo.save(ind);
				
		    	for(Reservation reserv: ind.getReservations()) {
		    		if(reserv.getId() == reservationToDelete.getId()) {
		    			log.info("VAR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		    		}
		    		log.info(reserv.getId());
		    	}
		    }
		    
		    Optional<Institution> userInst = institutionRepo.findById(profileId);
		    if(userInst.isPresent()) {
		    	log.info("hello??????");
		    	Institution inst = userInst.get();
		    	List<Reservation> updatedReserv = inst.getReservations();
		    	updatedReserv.remove(reservationToDelete);
		    	inst.setReservations(updatedReserv);
		    	institutionRepo.save(inst);
		    
		    	for(Reservation reserv: inst.getReservations()) {
		    		if(reserv.getId() == reservationToDelete.getId()) {
		    			log.info("VAR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		    		}
		    		log.info(reserv.getId());
		    	}
		    }
		    
		    log.info("REPODA BUL ERROR 1");


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

