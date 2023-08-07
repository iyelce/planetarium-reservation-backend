package com.payload;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationPayload {

	private int visitorCount;
    private LocalDate requestDate;
    private LocalTime requestTime;
    
    
	public ReservationPayload() {};	
    
	public ReservationPayload(int visitorCount, LocalDate requestDate, LocalTime requestTime) {
		super();
		this.visitorCount = visitorCount;
		this.requestDate = requestDate;
		this.requestTime = requestTime;
	}



	public int getVisitorCount() {
		return visitorCount;
	}
	public void setVisitorCount(int visitorCount) {
		this.visitorCount = visitorCount;
	}

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

	public LocalTime getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(LocalTime requestTime) {
		this.requestTime = requestTime;
	}

	@Override
	public String toString() {
		return "ReservationPayload [visitorCount=" + visitorCount + ", requestDate=" + requestDate + ", requestTime=" + requestTime + "]";
	}
    
    
    
}
