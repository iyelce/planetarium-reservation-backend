package com.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.models.Reservation;

@Repository
public interface ReservationRepo extends MongoRepository<Reservation, String> {

	

}
