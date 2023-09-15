package com.models;

import java.time.LocalTime;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "reservations")
public class Reservation {
	
	@Id private String id;
	private int visitorCount;
	private long requestDateTime;

	private Activity activity;

	
	private boolean approved;
	


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Reservation other = (Reservation) obj;
		return Objects.equals(id, other.id);
	}
	
	

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	@Override
	public String toString() {
		return "Reservation [visiterCount=" + visitorCount + ", requestDate=" + requestDateTime + ", approved=" + approved
				+ "]";
	}

	public Reservation() {};
	
	public Reservation(int visitorCount, long requestDateTime, boolean approved, Activity activity) {
		super();
		this.visitorCount = visitorCount;
		this.requestDateTime = requestDateTime;
		this.approved = approved;
		this.activity = activity;

	}

	public Reservation(String id, int visiterCount, long requestDateTime, LocalTime requestTime, boolean approved) {
		super();
		this.id = id;
		this.visitorCount = visiterCount;
		this.requestDateTime = requestDateTime;
		this.approved = approved;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getVisitorCount() {
		return visitorCount;
	}

	public void setVisitorCount(int visiterCount) {
		this.visitorCount = visiterCount;
	}


	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public long getRequestDateTime() {
		return requestDateTime;
	}

	public void setRequestDateTime(long requestDateTime) {
		this.requestDateTime = requestDateTime;
	}

	
	
}
