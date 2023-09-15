package com.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import com.models.Activity;
import com.models.Day;

import com.models.TimeSlot;
import com.payload.ActivityPayload;
import com.payload.ReservationPayload;
import com.repo.DayRepo;

@Service
public class DayServiceImpl implements DayService {

    @Autowired
    private DayRepo dayRepo;

    private static final Logger log = LoggerFactory.getLogger(DayServiceImpl.class);

    // rezervasyondan gun yarat
    @Override
    public Day createDayFromReservation(ReservationPayload reservationPayload) {
    	LocalDate reservationDate = reservationPayload.getRequestDate();
    	Activity activity = reservationPayload.getActivity();
        return createNewDay(reservationDate, activity.getCapacity(), activity);
    }

    // aktiviteden gun yarat
    @Override
    public Day createDayFromActivity(ActivityPayload activityPayload) {
    	LocalDate activityDate = activityPayload.getDate();
    	LocalDateTime localDateTime = activityDate.atStartOfDay();
        long epochMillis = localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        
        log.info("--date of activity: " + epochMillis);
        System.out.println("create day from activity icinde:   " + activityPayload.getSlotDuration());
    	String activityName = activityPayload.getActivity();
    	log.info("--activity name: " + activityName);
    	
    	Activity activity = new Activity(epochMillis, activityPayload.getCapacity(), true, activityName, activityPayload.getStartTime()
    			,activityPayload.getEndTime(), activityPayload.getSlotDuration());
    	log.info("--activity: " + activity.toString());
    	
    	
    	Day day = createNewDay(activityDate, activity.getCapacity(), activity);
    	dayRepo.save(day);
        return day;
    }

    // helper
    public Day createNewDay(LocalDate date, int slotCapacity, Activity activity) {
        Map<Long, TimeSlot> timeSlotsAvailability = new HashMap<>();


        log.info("date: " + date.toString());
        
        LocalTime startTime = activity.getStartTime();
        LocalTime endTime = activity.getEndTime();
        int slotDurationInHours = activity.getSlotDuration();
        log.info("activity details: " + startTime.toString() + " " + endTime.toString() + " " + slotDurationInHours);

        // Initialize the time slots from startTime to endTime with the specified slot duration and capacity
        while (startTime.isBefore(endTime)) {
        	log.info("iterate ediyo");
            LocalDateTime slotDateTime = LocalDateTime.of(date, startTime);
            log.info("date time: " + slotDateTime.toString());
            long slotTimeSinceEpoch = slotDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
            log.info("map null diye mi error veriyo??????");
            timeSlotsAvailability.put(slotTimeSinceEpoch, new TimeSlot(slotCapacity, true));
            log.info(timeSlotsAvailability.toString());
            log.info("yoksa baska bise mi??????");
            startTime = startTime.plusHours(slotDurationInHours);
            log.info("duration : " + slotDurationInHours);
            log.info("updated start time: " + startTime.toString());
        }
        

        activity.setTimeSlotsAvailability(timeSlotsAvailability);
        log.info("time slot kesinlikle hata veriyoooooooooooo  " + activity.getTimeSlotsAvailability().toString());
        
        if (dayRepo.findByDate(activity.getDate()) != null) {
        	Day day = dayRepo.findByDate(activity.getDate());
        	day.getActivities().add(activity);
        	return day;
        }
        
        
        Day day = new Day(date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli());
        log.info("day olustu aktiviteden " + day.toString());
        
        if (day.getActivities() == null) {
            day.setActivities(new ArrayList<>());
        }
        
        day.getActivities().add(activity);
        log.info("DAY AKTIVITELERI: " + day.getActivities().toString());
        
        // Return the new Day object
        return day;
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

