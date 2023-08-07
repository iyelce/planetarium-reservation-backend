package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.HashMap;
import com.models.Day;

import com.models.TimeSlot;
import com.payload.ReservationPayload;
import com.repo.DayRepo;

@Service
public class DayServiceImpl implements DayService {

    @Autowired
    private DayRepo dayRepo;

    // TODO: Define any other methods related to day operations

    @Override
    public Day createDayFromReservation(ReservationPayload reservationPayload, int defaultCapacityValue) {
    	LocalDate reservationDate = reservationPayload.getRequestDate();
        return createNewDay(reservationDate, defaultCapacityValue);
    }

    public Day createNewDay(LocalDate date, int slotCapacity) {
        Map<Long, TimeSlot> timeSlotsAvailability = new HashMap<>();

        //LocalDateTime midnightDateTime = date.atStartOfDay();
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(17, 0);
        int slotDurationInHours = 2;

        // Initialize the time slots from startTime to endTime with the specified slot duration and capacity
        while (startTime.plusHours(slotDurationInHours).isBefore(endTime) || startTime.plusHours(slotDurationInHours).equals(endTime)) {
            LocalDateTime slotDateTime = LocalDateTime.of(date, startTime);
            long slotTimeSinceEpoch = slotDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
            timeSlotsAvailability.put(slotTimeSinceEpoch, new TimeSlot(slotCapacity, true));
            startTime = startTime.plusHours(slotDurationInHours);
        }

        // Return the new Day object
        return new Day(date.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli(), timeSlotsAvailability);
    }

}

