package com.models;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.payload.IndividualRegisterPayload;

@Document(collection = "individuals")
public class Individual {

	@Id private String id;
	
	private String idNumber;
	private String name;
	private String surname;
	private String birthday;
	private String username;
	private String password;

	private List<Reservation> reservations;
	

	
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

	// Default constructor
    public Individual() {}

    // Custom getter to ensure the reservations list is never null
    public List<Reservation> getReservations() {
        if (reservations == null) {
            reservations = new ArrayList<>();
        }
        return reservations;
    }
	

    
	
	public Individual(String idNumber, String name, String surname, String birthday, String username, String password,
			List<Reservation> reservations) {
		super();
		this.idNumber = idNumber;
		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.username = username;
		this.password = password;
		this.reservations = reservations;
	}

	public Individual(String id, String idNumber, String name, String surname, String birthday, String username,
			String password, List<Reservation> reservations) {
		super();
		this.id = id;
		this.idNumber = idNumber;
		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.username = username;
		this.password = password;
		this.reservations = reservations;
	}

	public Individual(IndividualRegisterPayload payload) {

		this.idNumber = payload.getIdNumber();
		this.name = payload.getName();
		this.surname = payload.getSurname();
		this.birthday = payload.getBirthday();
		this.username = payload.getUsername();
		this.password = payload.getPassword();
	}

	@Override
	public String toString() {
		return "Individual [idNumber=" + idNumber + ", name=" + name + ", surname=" + surname + ", birthday=" + birthday
				+ "]";
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

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public void setReservations(List<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	
	
	
	
	
}
