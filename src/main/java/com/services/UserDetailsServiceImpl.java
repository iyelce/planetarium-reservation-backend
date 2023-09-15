// default user details service i implement ediyor
// guvenlik kontrollerinde kullanildi
package com.services;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;


import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.models.Admin;
import com.models.Individual;
import com.models.Institution;
import com.repo.AdminRepo;
import com.repo.IndividualRepo;
import com.repo.InstitutionRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AdminRepo adminRepository;
    @Autowired
    private InstitutionRepo institutionRepository;
    @Autowired
    private IndividualRepo individualRepository;
    
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        

        // Try to find the user in the institution repository
        Institution institution = institutionRepository.findByUsername(username);
        if (institution != null) {
        	log.info("USERDETAILSSERVICE    INSTT VAR");
        	log.info(username + " " + institution.getPassword() + " " + institution.getAuthorities().toString());
            return createUserDetails(institution.getUsername(), institution.getPassword(), institution.getAuthorities());
        }

     // Try to find the user in the admin repository
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null) {
        	log.info("ADMIN VAR");
            return createUserDetails(admin.getUsername(), admin.getPassword(), admin.getAuthorities());
        }
        
        // Try to find the user in the individual repository
        Individual individual = individualRepository.findByUsername(username);
        if (individual != null) {
        	log.info("INDIVVV VAR");
            return createUserDetails(individual.getUsername(), individual.getPassword(), individual.getAuthorities());
        }

        log.info("USER YOKKK");
        throw new UsernameNotFoundException("User not found");
    }

    private UserDetails createUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
    	UserDetails user = new org.springframework.security.core.userdetails.User(
                username,
                password,
                authorities
            );
    	
    	log.info(user.toString());
    	
        return user;
    }
}