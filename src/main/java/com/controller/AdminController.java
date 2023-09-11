package com.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controller.AuthController.CustomException;
import com.filter.JwtTokenUtil;
import com.models.Admin;
import com.models.Individual;
import com.models.Institution;
import com.models.Reservation;
import com.payload.AdminPayload;
import com.payload.InstitutionRegisterPayload;
import com.repo.ReservationRepo;
import com.services.AdminService;
//import com.services.UserDetailsService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	private static final Logger log = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private AdminService adminService;
	@Autowired 
	private ReservationRepo reservationRepo;
	@Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailService;
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	// add another admin
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
            // Authenticate the individual user and generate a token
			Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(payload.getUsername(), payload.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("ADMIN AUTHENTICATED");
            UserDetails userDetails = userDetailService.loadUserByUsername(authentication.getName());
            String token = jwtTokenUtil.generateToken(userDetails);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
	}

	
	// delete reservations
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/reservation/{reservationId}")
    public ResponseEntity<?> deleteReservation(@PathVariable String reservationId) {
        adminService.deleteReservation(reservationId);
        log.info("Reservation DELETED");
        return ResponseEntity.ok().body("Deleted");
    }

    // return admin profile
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/profile/{profileId}")
    public ResponseEntity<?> getAdminProfileById(@PathVariable String profileId) {
        Admin profileAdmin = adminService.getProfileById(profileId);
        log.info("admin RETURNED");
        return ResponseEntity.ok(profileAdmin);
    }

	
    // return all the reservations
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/all-reservations")
    public ResponseEntity<?> getAllReservations() {
        List<Reservation> reservations = reservationRepo.findAll();
        log.info("All reservations RETURNED");
        return ResponseEntity.ok(reservations);
    }
	
	
}
