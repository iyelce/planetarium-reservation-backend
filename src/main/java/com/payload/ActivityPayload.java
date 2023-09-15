package com.payload;

import java.time.LocalDate;
import java.time.LocalTime;


public class ActivityPayload {

	private String activity;
	private LocalDate date; // Store date in UTC
    private LocalTime startTime;
    private LocalTime endTime;
	private int capacity;
	private int slotDuration;
	
	
	
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getSlotDuration() {
		return slotDuration;
	}
	public void setSlotDuration(int slotDuration) {
		this.slotDuration = slotDuration;
	}
	

	
}
