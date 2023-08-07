package com.services;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.models.Day;
import com.models.Reservation;
import com.payload.ReservationPayload;
import com.services.ReservationServiceImpl.CustomException;

public interface DayService {
	public Day createDayFromReservation(ReservationPayload reservationPayload, int defaultCapacityValue) throws CustomException;
	Day createNewDay(LocalDate date, int slotCapacity) throws CustomException;
}
