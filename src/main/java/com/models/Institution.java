package com.models;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.models.Institution;
import com.payload.InstitutionRegisterPayload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Document(collection = "institutions")
public class Institution {

	@Id private String id;
	@NotBlank private String username;
	@NotBlank private String password;
	@NotBlank @Email private String email;
	
	private List<Reservation> reservations;
	
	
	// Default constructor
    public Institution() {
        this.reservations = new ArrayList<>(); // Initialize the reservations list as an empty ArrayList
    }

    // Custom getter to ensure the reservations list is never null
    public List<Reservation> getReservations() {
        if (reservations == null) {
            reservations = new ArrayList<>();
        }
        return reservations;
    }
	
	public Institution(InstitutionRegisterPayload payload) {
		super();
		this.username = payload.getUsername();
		this.password = payload.getPassword();
		this.email = payload.getEmail();
		
	}

	
	public Institution(@NotBlank String username, @NotBlank String password, @NotBlank @Email String email,
			List<Reservation> reservations) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.reservations = reservations;
	}




	public Institution(String id, @NotBlank String username, @NotBlank String password, @NotBlank @Email String email,
			List<Reservation> reservations) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.reservations = reservations;
	}




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
		Institution other = (Institution) obj;
		return Objects.equals(id, other.id);
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", email=" + email + "]";
	}



	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}


	
	
	
}
