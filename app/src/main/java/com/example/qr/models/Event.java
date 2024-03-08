package com.example.qr.models;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.GeoPoint;

import java.io.Serializable;
import java.util.Date;

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

    // Constructor, getters, and setters

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
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public GeoPoint getLocation() {
        return location;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getEventPoster() {
        return eventPoster;
    }

    public void setEventPoster(String eventPoster) {
        this.eventPoster = eventPoster;
    }

    public Integer getAttendeeLimit() {
        return attendeeLimit;
    }

    public void setAttendeeLimit(Integer attendeeLimit) {
        this.attendeeLimit = attendeeLimit;
    }

    @NonNull
    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", organizerId='" + organizerId + '\'' +
                ", eventDate=" + eventDate +
                ", location=" + location +
                ", qrCode='" + qrCode + '\'' +
                ", eventPoster='" + eventPoster + '\'' +
                ", attendeeLimit=" + attendeeLimit +
                '}';
    }

}

