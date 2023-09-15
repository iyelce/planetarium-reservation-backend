package com.services;



import com.models.Day;
import com.payload.ActivityPayload;
import com.payload.ReservationPayload;
import com.services.ReservationServiceImpl.CustomException;

public interface DayService {
	public Day createDayFromReservation(ReservationPayload reservationPayload) throws CustomException;
	public Day createDayFromActivity(ActivityPayload activityPayload) throws CustomException;
}
