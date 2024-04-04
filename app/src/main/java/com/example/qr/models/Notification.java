package com.example.qr.models;

import androidx.annotation.NonNull;
import java.util.Date;

/**
 * Represents a notification associated with an event. Contains information
 * about the notification message and the time it was sent.
 */
public class Notification {
    private String id; // Document ID in Firestore
    private String eventId; // Reference to Event document ID
    private String message;
    private Date sentTime;
    private Boolean readStatus;

    /**
     * Default constructor required for Firestore data mapping.
     */
    public Notification() {
    }

    /**
     * Constructs a new Notification instance.
     *
     * @param id The unique identifier of the notification in Firestore.
     * @param eventId The ID of the event associated with this notification.
     * @param message The notification message content.
     * @param sentTime The date and time when the notification was sent.
     */
    public Notification(String id, String eventId, String message, Date sentTime, Boolean readStatus) {
        this.id = id;
        this.eventId = eventId;
        this.message = message;
        this.sentTime = sentTime;
        this.readStatus = readStatus;
    }

    /**
     * Gets the Firestore document ID of the notification.
     *
     * @return The unique identifier of the notification.
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the Firestore document ID of the notification.
     *
     * @param id The new identifier of the notification.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Gets the ID of the event associated with this notification.
     *
     * @return The associated event's ID.
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Sets the ID of the event associated with this notification.
     *
     * @param eventId The new associated event's ID.
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     * Gets the notification message content.
     *
     * @return The notification message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the notification message content.
     *
     * @param message The new notification message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the date and time when the notification was sent.
     *
     * @return The sent time of the notification.
     */
    public Date getSentTime() {
        return sentTime;
    }

    /**
     * Sets the date and time when the notification was sent.
     *
     * @param sentTime The new sent time of the notification.
     */
    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }
    public void setReadStatus(Boolean readStatus){this.readStatus = readStatus;}

    public Boolean getReadStatus(){return this.readStatus;}
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
