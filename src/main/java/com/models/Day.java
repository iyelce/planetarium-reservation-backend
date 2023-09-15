package com.models;

import java.util.ArrayList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "days")
public class Day {
	@Id private String id;
	
	private long date; // Store date in UTC
    private ArrayList<Activity> activities;
	
	
    public Day() {};
    
    public Day(String id, long date, ArrayList<Activity> activity) {
		super();
		this.id= id;
		this.date = date;
		this.activities = activity;
	}
    

	public ArrayList<Activity> getActivities() {
		return activities;
	}

	public void setActivities(ArrayList<Activity> activities) {
		this.activities = activities;
	}

	public Day(long date) {
		super();
		this.date = date;
	}

	public long getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Day [date=" + date + "]";
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



    
}

