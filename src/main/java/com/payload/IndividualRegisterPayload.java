package com.payload;

import jakarta.validation.constraints.NotBlank;

public class IndividualRegisterPayload {

	@NotBlank
	private String name;
	@NotBlank
	private String surname;
	@NotBlank
	private String phone;
	@NotBlank
	private String password;
	@NotBlank
	private String username;
	
	
	
	
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	
	
	
	
}
