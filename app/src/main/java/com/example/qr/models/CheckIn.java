package com.example.qr.models;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.GeoPoint;
import java.util.Date;
public class CheckIn {
    private String id; // Document ID in Firestore
    private String eventId; // Reference to Event document ID
    private String userId; // Reference to User document ID
    private Date checkInTime;
    private GeoPoint location;

    // Constructor, getters, and setters
    public CheckIn() {

    }

    public CheckIn(String id, String eventId, String userId, Date checkInTime, GeoPoint location) {
        this.id = id;
        this.eventId = eventId;
        this.userId = userId;
        this.checkInTime = checkInTime;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    @NonNull
    @Override
    public String toString() {
        return "CheckIn{" +
                "id='" + id + '\'' +
                ", eventId='" + eventId + '\'' +
                ", userId='" + userId + '\'' +
                ", checkInTime=" + checkInTime +
                ", location=" + location +
                '}';
    }

}
