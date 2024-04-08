package com.example.qr.models;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.Date;

/**
 * Unit tests for the Notification class.
 */
public class NotificationTest {

    /**
     * Test the creation of a Notification object.
     */
    @Test
    public void testNotificationCreation() {
        Date sentTime = new Date();
        Notification notification = new Notification("notif123", "event789", "Welcome to the event!", sentTime, true);

        assertEquals("notif123", notification.getId());
        assertEquals("event789", notification.getEventId());
        assertEquals("Welcome to the event!", notification.getMessage());
        assertEquals(sentTime, notification.getSentTime());
    }

    /**
     *  Test the setters and getters for the Notification class.
     */
    @Test
    public void testSettersAndGetters() {
        Notification notification = new Notification("notif123", "event789", "Welcome to the event!", new Date(), true);
        notification.setId("notif456");
        notification.setEventId("event1011");
        notification.setMessage("Event update: New schedule.");
        notification.setSentTime(new Date(0)); // Setting a specific time for testing

        assertEquals("notif456", notification.getId());
        assertEquals("event1011", notification.getEventId());
        assertEquals("Event update: New schedule.", notification.getMessage());
        assertEquals(new Date(0), notification.getSentTime());
    }
}

