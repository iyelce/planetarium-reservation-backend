package com.controller;

import java.time.LocalDate;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.models.Individual;
import com.payload.IndividualUpdatePayload;
import com.repo.IndividualRepo;
import com.services.IndividualService;


// making a reservation -> id and birthday update coming from front-end

@RestController
@RequestMapping("/individual")
public class IndividualController {

    @Autowired
    private IndividualRepo individualRepo;
    
    @Autowired
    private IndividualService individualService;

    @PutMapping("/update-profile/{profileId}")
    public ResponseEntity<?> updateProfile(@PathVariable String profileId, @RequestBody IndividualUpdatePayload request) {
        try {
            String idNumber = request.getIdNumber();
            LocalDate birthday = request.getBirthday();
            long dateSinceEpoch = birthday.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli();

           // Retrieve the individual using the profile ID
            Individual individual = individualService.getProfileById(profileId);

            if (individual == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Individual not found.");
            }

            // Update the individual's profile
            individual.setIdNumber(idNumber);
            individual.setBirthday(dateSinceEpoch);
            individualRepo.save(individual);

            return ResponseEntity.ok("Profile updated successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update profile.");
        }
    }
}