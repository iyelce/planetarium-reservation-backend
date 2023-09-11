package com.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.payload.IndividualRegisterPayload;

@Document(collection = "individuals")
public class Individual {

    @Id private String id;

    private String idNumber;
    private String name;
    private String surname;
    private long birthday;
    private String username;
    private String password;
    
    private GrantedAuthority authority;
    
    private List<Reservation> reservations;
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Individual other = (Individual) obj;
		return Objects.equals(id, other.id);
	}

	public Individual(IndividualRegisterPayload payload) {
		this.name = payload.getName();
		this.password = payload.getPassword();
		this.surname = payload.getSurname();
		this.username = payload.getUsername();
	}
	
	
	

	public Individual(String idNumber, String name, String surname, long birthday, String username, String password) {
		super();
		this.idNumber = idNumber;
		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.username = username;
		this.password = password;
	}

	
	
	// Set the role as authority
    public void setRole(String role) {
        this.authority = new SimpleGrantedAuthority(role);
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(authority);
    }
	
	
	@Override
	public String toString() {
		return "Individual [idNumber=" + idNumber + ", name=" + name + ", surname=" + surname + ", birthday=" + birthday
				+ ", username=" + username + ", password=" + password + "]";
	}

	// Default constructor
    public Individual() {}

    // Custom getter to ensure the reservations list is never null
    public List<Reservation> getReservations() {
        if (reservations == null) {
            reservations = new ArrayList<>();
        }
        return reservations;
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public long getBirthday() {
		return birthday;
	}

	public void setBirthday(long birthday) {
		this.birthday = birthday;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	

 
    	
	
	
}
