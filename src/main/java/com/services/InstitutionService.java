package com.services;


import java.util.List;

import com.models.Reservation;
import com.models.Institution;
import com.payload.InstitutionLoginPayload;
import com.payload.InstitutionRegisterPayload;
import com.services.ReservationServiceImpl.CustomException;


public interface InstitutionService {
	 Institution createUser(InstitutionRegisterPayload user) throws CustomException;
	 Institution loginUser(InstitutionLoginPayload loginUser) throws CustomException;
	 Institution getProfileById(String profileId) throws CustomException;
	 List<Reservation> getProfileReservations(String profileId) throws CustomException;
}

