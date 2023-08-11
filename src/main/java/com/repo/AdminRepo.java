package com.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.models.Admin;
import com.models.Reservation;

public interface AdminRepo extends MongoRepository<Admin, String> {
    Admin findByUsername(String username);
    //List<Reservation> findAllReservations();
}

