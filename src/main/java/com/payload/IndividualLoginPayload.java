package com.payload;

import jakarta.validation.constraints.NotBlank;

public class IndividualLoginPayload {
	
	@NotBlank
	private String idNumber;

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	

}
