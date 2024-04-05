package com.example.qr.models;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents an event stored in Firestore. Contains information about the event title,
 * description, organizer, date, location, QR code, event poster, and attendee limit.
 */
// Event needs to implement Serializable to Event List and User Profile List implementation for Administrator part
public class Event implements Serializable {
    private String id; // Document ID in Firestore
    private String title;
    private String description;
    private String organizerId; // Reference to User document ID
    private Date eventDate;
    private GeoPoint location;
    private String qrCode;
    private String eventPoster;
    private Integer attendeeLimit; // Optional

    private Integer attendeeCount;



    /**
     * Default constructor required for Firestore data mapping.
     */
    public Event() {

    }

    public Event(String id, String title, String description, String organizerId, Date eventDate,
                 GeoPoint location, String qrCode, String eventPoster, Integer attendeeLimit) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.organizerId = organizerId;
        this.eventDate = eventDate;
        this.location = location;
        this.qrCode = qrCode;
        this.eventPoster = eventPoster;
        this.attendeeLimit = attendeeLimit;
        this.attendeeCount = 0;
    }

    /**
     * Returns the document ID of the event.
     *
     * @return The document ID of the event.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the document ID of the event.
     *
     * @param id The document ID of the event.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the title of the event.
     *
     * @return The title of the event.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the event.
     *
     * @param title The title of the event.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the description of the event.
     *
     * @return The description of the event.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the event.
     *
     * @param description The description of the event.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the document ID of the event organizer.
     *
     * @return The document ID of the event organizer.
     */
    public String getOrganizerId() {
        return organizerId;
    }

    /**
     * Sets the document ID of the event organizer.
     *
     * @param organizerId The document ID of the event organizer.
     */
    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    /**
     * Returns the date of the event.
     *
     * @return The date of the event.
     */
    public Date getEventDate() {
        return eventDate;
    }

    /**
     * Sets the date of the event.
     *
     * @param eventDate The date of the event.
     */
    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    /**
     * Returns the location of the event.
     *
     * @return The location of the event.
     */
    public GeoPoint getLocation() {
        return location;
    }

    /**
     * Sets the location of the event.
     *
     * @param location The location of the event.
     */
    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    /**
     * Returns the QR code of the event.
     *
     * @return The QR code of the event.
     */
    public String getQrCode() {
        return qrCode;
    }

    /**
     * Sets the QR code of the event.
     *
     * @param qrCode The QR code of the event.
     */
    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    /**
     * Returns the poster of the event.
     *
     * @return The poster of the event.
     */
    public String getEventPoster() {
        return eventPoster;
    }

    /**
     * Sets the poster of the event.
     *
     * @param eventPoster The poster of the event.
     */
    public void setEventPoster(String eventPoster) {
        this.eventPoster = eventPoster;
    }

    /**
     * Returns the attendee limit of the event.
     *
     * @return The attendee limit of the event.
     */
    public Integer getAttendeeLimit() {
        return attendeeLimit;
    }

    /**
     * Sets the attendee limit of the event.
     *
     * @param attendeeLimit The attendee limit of the event.
     */
    public void setAttendeeLimit(Integer attendeeLimit) {
        this.attendeeLimit = attendeeLimit;
    }


    /**
     * Returns the attendee count of the event.
     *
     * @return The attendee count of the event.
     */
    public Integer getAttendeeCount() {
        return attendeeCount;

    }

    /**
     * Sets the attendee count of the event.
     *
     * @param attendeeCount The attendee count of the event.
     */
    public void setAttendeeCount(Integer attendeeCount) {
        this.attendeeCount = attendeeCount;
    }

    /**
     * Returns a string representation of the event.
     *
     * @return A string representation of the event.
     */
    @NonNull
    @Override
    public String toString() {
        return getTitle() + " " + getId();
    }

}

