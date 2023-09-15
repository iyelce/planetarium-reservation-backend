package com.payload;

import java.time.LocalDate;
import java.time.LocalTime;

import com.models.Activity;

public class ReservationPayload {

	private int visitorCount;
    private LocalDate requestDate;
    private LocalTime requestTime;
    
    private Activity activity;
    
    
	public ReservationPayload() {};	
    
	public ReservationPayload(int visitorCount, LocalDate requestDate, LocalTime requestTime, Activity activity) {
		super();
		this.visitorCount = visitorCount;
		this.requestDate = requestDate;
		this.requestTime = requestTime;
		this.activity = activity;
	}


	
	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
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
		return "ReservationPayload [visitorCount=" + visitorCount + ", requestDate=" + requestDate + ", requestTime="
				+ requestTime + ", activity=" + activity + "]";
	}

	  
    
}
