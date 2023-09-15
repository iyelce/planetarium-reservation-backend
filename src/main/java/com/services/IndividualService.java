package com.services;

import java.util.List;


import com.models.Individual;
import com.models.Reservation;
import com.payload.IndividualLoginPayload;
import com.payload.IndividualRegisterPayload;
import com.services.ReservationServiceImpl.CustomException;


public interface IndividualService {
	Individual createUser(IndividualRegisterPayload user) throws CustomException;
	Individual loginUser(IndividualLoginPayload loginUser) throws CustomException;
	Individual getProfileById(String profileId) throws CustomException;
	List<Reservation> getProfileReservations(String profileId) throws CustomException;
}