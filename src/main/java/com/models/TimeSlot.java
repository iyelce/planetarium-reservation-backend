package com.models;


public class TimeSlot {
    private int capacity;
    private boolean available;


    public TimeSlot(int capacity, boolean available) {
        this.capacity = capacity;
        this.available = available;
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
		return "TimeSlot [capacity=" + capacity + ", available=" + available + "]";
	}

  
}


