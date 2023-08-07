package com.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.models.Individual;

@Repository
public interface IndividualRepo extends MongoRepository<Individual, String> {
	Individual findByIdNumber(String idNumber);
	Individual findByUsername(String username);

}
