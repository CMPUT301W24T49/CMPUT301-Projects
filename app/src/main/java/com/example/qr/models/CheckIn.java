package com.example.qr.models;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.GeoPoint;
import java.util.Date;

/**
 * Represents an event stored in Firestore. Contains information about the event title,
 * description, organizer, date, location, QR code, event poster, and attendee limit.
 */
public class CheckIn {
    private String eventId; // Reference to Event document ID
    private String userId; // Reference to User document ID
    private Date checkInTime;
    private GeoPoint location;

    /**
     * Default constructor required for Firestore data mapping.
     */
    public CheckIn() {

    }

    public CheckIn(String eventId, String userId, Date checkInTime, GeoPoint location) {
        this.eventId = eventId;
        this.userId = userId;
        this.checkInTime = checkInTime;
        this.location = location;
    }

    /**
     * Returns the document ID of the event.
     *
     * @return The document ID of the event.
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Sets the document ID of the event.
     *
     * @param eventId The document ID of the event.
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * Returns the document ID of the user.
     *
     * @return The document ID of the user.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the document ID of the user.
     *
     * @param userId The document ID of the user.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Returns the check-in time.
     *
     * @return The check-in time.
     */
    public Date getCheckInTime() {
        return checkInTime;
    }

    /**
     * Sets the check-in time.
     *
     * @param checkInTime The check-in time.
     */
    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    /*  Returns the location of the check-in.
     *
     * @return The location of the check-in.
     */
    public GeoPoint getLocation() {
        return location;
    }

    /**
     * Sets the location of the check-in.
     *
     * @param location The location of the check-in.
     */
    public void setLocation(GeoPoint location) {
        this.location = location;
    }

}
