package com.payload;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

import com.models.TimeSlot;

public class DayPayload {

    private LocalDate date;
    private Map<LocalTime, TimeSlot> timeSlotsAvailability;

    public DayPayload() {}

    public DayPayload(LocalDate date, Map<LocalTime, TimeSlot> timeSlotsAvailability) {
        this.date = date;
        this.timeSlotsAvailability = timeSlotsAvailability;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Map<LocalTime, TimeSlot> getTimeSlotsAvailability() {
        return timeSlotsAvailability;
    }

    public void setTimeSlotsAvailability(Map<LocalTime, TimeSlot> timeSlotsAvailability) {
        this.timeSlotsAvailability = timeSlotsAvailability;
    }
}

