package com.repo;

import java.time.LocalDate;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.models.Day;

@Repository
public interface DayRepo extends MongoRepository<Day, LocalDate> {
    Day findByDate(long date);
}

