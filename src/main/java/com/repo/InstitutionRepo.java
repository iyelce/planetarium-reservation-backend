package com.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.models.Institution;

@Repository
public interface InstitutionRepo extends MongoRepository<Institution, String>{
	public Institution findByUsername(String username);
	public Institution findByUsernameAndPassword(String username, String password);


}
