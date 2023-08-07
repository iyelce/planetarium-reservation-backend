package com.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "days")
public class Day {
	@Id private String id;
	
	private long date; // Store date in UTC
    private Map<Long, TimeSlot> timeSlotsAvailability;
    
    public Day() {};
    
    public Day(String id, long date, Map<Long, TimeSlot> timeSlotsAvailability ) {
		super();
		this.id= id;
		this.date = date;
		this.timeSlotsAvailability = timeSlotsAvailability;
	}
    

	public Day(long date, Map<Long, TimeSlot> timeSlotsAvailability) {
		super();
		this.date = date;
		this.timeSlotsAvailability = timeSlotsAvailability;
	}

	public long getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Day [date=" + date + ", timeSlotsAvailability=" + timeSlotsAvailability + "]";
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public Map<Long, TimeSlot> getTimeSlotsAvailability() {
		return timeSlotsAvailability;
	}

	public void setTimeSlotsAvailability(Map<Long, TimeSlot> timeSlotsAvailability) {
		this.timeSlotsAvailability = timeSlotsAvailability;
	}

	

    
}

