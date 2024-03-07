package com.example.qr.models;

import androidx.annotation.NonNull;

import java.util.Date;

public class Notification {
    private final String id; // Document ID in Firestore
    private final String eventId; // Reference to Event document ID
    private String message;
    private Date sentTime;

    // Constructor, getters, and setters

    public Notification(String id, String eventId, String message, Date sentTime) {
        this.id = id;
        this.eventId = eventId;
        this.message = message;
        this.sentTime = sentTime;
    }

    public String getId() {
        return id;
    }

    public String getEventId() {
        return eventId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getSentTime() {
        return sentTime;
    }

    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }

    @NonNull
    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", eventId='" + eventId + '\'' +
                ", message='" + message + '\'' +
                ", sentTime=" + sentTime +
                '}';
    }
}
