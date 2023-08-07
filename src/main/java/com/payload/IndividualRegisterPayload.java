package com.payload;

import jakarta.validation.constraints.NotBlank;

public class IndividualRegisterPayload {

	@NotBlank
	private String idNumber;
	@NotBlank
	private String name;
	@NotBlank
	private String surname;
	@NotBlank
	private String birthday;
	
	
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
	
	
	
	
	
}
