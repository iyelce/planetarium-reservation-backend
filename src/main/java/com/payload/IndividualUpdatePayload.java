package com.payload;

import java.time.LocalDate;

public class IndividualUpdatePayload {
	
	private String idNumber;
	private LocalDate birthday;
	
	
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public LocalDate getBirthday() {
		return birthday;
	}
	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}
	

}
