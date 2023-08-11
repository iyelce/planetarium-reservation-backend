package com.services;

import org.springframework.stereotype.Service;

import com.models.Admin;
import com.payload.AdminPayload;
import com.services.ReservationServiceImpl.CustomException;

@Service
public interface AdminService {

	Admin createAdmin(AdminPayload user) throws CustomException;
	Admin loginAdmin(AdminPayload loginUser) throws CustomException;
	Admin getProfileById(String profileId) throws CustomException;
	void deleteReservation(String reservationId);

}
