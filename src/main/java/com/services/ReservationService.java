package com.services;

import com.models.Reservation;
import com.payload.ReservationPayload;


public interface ReservationService {
    Reservation createInstitutionReservation(ReservationPayload reservation, String profileId);
    Reservation createIndividualReservation(ReservationPayload reservationPayload, String profileId);
    void deleteReservation(String reservationId, String profileId);
}