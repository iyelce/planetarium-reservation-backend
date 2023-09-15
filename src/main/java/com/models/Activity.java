// aktivite class: day class da array olarak kullanıldı
package com.models;

import java.time.LocalTime;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.annotation.Id;

public class Activity {

	@Id private String activityName;
	
	// aktivite zamani
	private long date; // Store date in UTC
	
	// aktivitenin gun icindeki saat araliklari
    private Map<Long, TimeSlot> timeSlotsAvailability;
    private LocalTime startTime;
    private LocalTime endTime;
	private int capacity;
	
	// saat araliklari: int olarak alindi rezervasyonlar saat basi yapilmali
	private int slotDuration;
	
	private boolean available;

	public Activity() {}
	
	public Activity(long date, Map<Long, TimeSlot> timeSlotsAvailability, int capacity, boolean available, String name, int duration) {
		super();
		this.date = date;
		this.timeSlotsAvailability = timeSlotsAvailability;
		this.capacity = capacity;
		this.available = available;
		this.activityName = name;
		this.slotDuration = duration;
	}
	
	public Activity(long date, int capacity, boolean available, String name, LocalTime startTime, LocalTime endTime, int duration) {
		super();
		this.date = date;
		this.capacity = capacity;
		this.available = available;
		this.activityName = name;
		this.startTime = startTime;
		this.endTime = endTime;
		this.slotDuration = duration;
	}

	

	public String getActivityName() {
		return activityName;
	}



	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}



	public int getSlotDuration() {
		return slotDuration;
	}



	public void setSlotDuration(int slotDuration) {
		this.slotDuration = slotDuration;
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


	public long getDate() {
		return date;
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

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "Activities [date=" + date + ", timeSlotsAvailability=" + timeSlotsAvailability + ", capacity="
				+ capacity + ", available=" + available + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(activityName, date);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		return Objects.equals(activityName, other.activityName) && date == other.date;
	}
	
	
	
}
