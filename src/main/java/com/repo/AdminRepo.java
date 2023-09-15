package com.repo;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.models.Admin;

public interface AdminRepo extends MongoRepository<Admin, String> {
    Admin findByUsername(String username);
}

