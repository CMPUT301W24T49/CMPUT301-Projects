package com.example.qr.models;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.google.firebase.firestore.GeoPoint;
import java.util.Date;

/**
 * Unit tests for the Event class.
 */
public class EventTest {

    /**
     * Test the creation of an Event object.
     */
    @Test
    public void testEventCreation() {
        GeoPoint location = new GeoPoint(37.7749, -122.4194); // Example coordinates
        Date eventDate = new Date();
        Event event = new Event("event123", "Tech Conference", "Annual tech gathering", "user123",
                eventDate, location, "http://example.com/qrcode.jpg", "http://example.com/poster.jpg", 100);

        assertEquals("event123", event.getId());
        assertEquals("Tech Conference", event.getTitle());
        assertEquals("Annual tech gathering", event.getDescription());
        assertEquals("user123", event.getOrganizerId());
        assertEquals(eventDate, event.getEventDate());
        assertEquals(location, event.getLocation());
        assertEquals("http://example.com/qrcode.jpg", event.getQrCode());
        assertEquals("http://example.com/poster.jpg", event.getEventPoster());
        assertEquals((Integer) 100, event.getAttendeeLimit());
    }

    /**
     *  Test the setters and getters for the Event class.
     */
    @Test
    public void testSettersAndGetters() {
        Event event = new Event();
        event.setId("event456");
        event.setTitle("Science Fair");
        event.setDescription("A local community science fair for all ages.");
        event.setOrganizerId("user456");

        Date newEventDate = new Date();
        event.setEventDate(newEventDate);

        GeoPoint newLocation = new GeoPoint(40.7128, -74.0060); // Example coordinates
        event.setLocation(newLocation);

        event.setQrCode("http://example.com/new_qrcode.jpg");
        event.setEventPoster("http://example.com/new_poster.jpg");
        event.setAttendeeLimit(200);

        assertEquals("event456", event.getId());
        assertEquals("Science Fair", event.getTitle());
        assertEquals("A local community science fair for all ages.", event.getDescription());
        assertEquals("user456", event.getOrganizerId());
        assertEquals(newEventDate, event.getEventDate());
        assertEquals(newLocation, event.getLocation());
        assertEquals("http://example.com/new_qrcode.jpg", event.getQrCode());
        assertEquals("http://example.com/new_poster.jpg", event.getEventPoster());
        assertEquals((Integer) 200, event.getAttendeeLimit());
    }
}