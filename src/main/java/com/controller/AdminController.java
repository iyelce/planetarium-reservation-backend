package com.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controller.AuthController.CustomException;
import com.models.Admin;
import com.models.Individual;
import com.models.Institution;
import com.models.Reservation;
import com.payload.AdminPayload;
import com.payload.InstitutionRegisterPayload;
import com.repo.ReservationRepo;
import com.services.AdminService;

//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;

@RestController

@RequestMapping("/admin")
//@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class AdminController {
	private static final Logger log = LoggerFactory.getLogger(ReservationController.class);

	@Autowired
	private AdminService adminService;
	@Autowired 
	private ReservationRepo reservationRepo;
	
	// add another admin
    @PostMapping("/add-admin")
    public ResponseEntity<?> createAdmin(@RequestBody AdminPayload payload) {
        try {
            Admin createdUser = adminService.createAdmin(payload);
            log.info("admin CREATED");
            return ResponseEntity.ok(createdUser);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

	// admin login
	@PostMapping("/login")
	public ResponseEntity<?> loginAdmin(@RequestBody AdminPayload payload){
		try {
			Admin loginedAdmin = adminService.loginAdmin(payload);
			log.info("admin LOGGED IN");
			return ResponseEntity.ok(loginedAdmin);
		} catch (CustomException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}
	
	// delete reservations
    @DeleteMapping("/reservation/{reservationId}")
    public ResponseEntity<?> deleteReservation(@PathVariable String reservationId) {
        adminService.deleteReservation(reservationId);
        log.info("Reservation DELETED");
        return ResponseEntity.ok().body("Deleted");
    }

    // return admin profile
    @GetMapping("/profile/{profileId}")
    public ResponseEntity<?> getAdminProfileById(@PathVariable String profileId) {
        Admin profileAdmin = adminService.getProfileById(profileId);
        log.info("admin RETURNED");
        return ResponseEntity.ok(profileAdmin);
    }

	
    // return all the reservations
    @GetMapping("/all-reservations")
    public ResponseEntity<?> getAllReservations() {
        List<Reservation> reservations = reservationRepo.findAll();
        log.info("All reservations RETURNED");
        return ResponseEntity.ok(reservations);
    }
/*

	// check whether the user is admin
	private boolean isAdmin(Authentication authentication) {
		authentication.getAuthorities();
	    return authentication.getAuthorities().stream()
	            .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
	}

	*/
	
}
