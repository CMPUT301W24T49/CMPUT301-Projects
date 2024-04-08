package com.example.qr.models;

import static com.example.qr.activities.MainActivity.androidId;
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
        Event event = new Event("eventId", "eventTitle", "description.getText().toString()", "androidId",
                eventDate, eventDate,
                "startTime.getText().toString()", "endTime.getText().toString()", new GeoPoint(location.getLatitude(),
                location.getLongitude()), "eventQr", "eventPromo", "", 99);

        assertEquals("eventId", event.getId());
        assertEquals("eventTitle", event.getTitle());
        assertEquals("description.getText().toString()", event.getDescription());
        assertEquals("androidId", event.getOrganizerId());
        assertEquals(eventDate, event.getStartDate());
        assertEquals(eventDate, event.getEndDate());
        assertEquals("startTime.getText().toString()", event.getStartTime());
        assertEquals("endTime.getText().toString()", event.getEndTime());
        assertEquals(location, event.getLocation());
        assertEquals("eventQr", event.getQrCode());
        assertEquals("eventPromo", event.getQrpCode());
        assertEquals("", event.getEventPoster());
        assertEquals((Integer) 99, event.getAttendeeLimit());
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
        event.setStartDate(newEventDate);

        GeoPoint newLocation = new GeoPoint(40.7128, -74.0060); // Example coordinates
        event.setLocation(newLocation);

        event.setQrCode("http://example.com/new_qrcode.jpg");
        event.setEventPoster("http://example.com/new_poster.jpg");
        event.setAttendeeLimit(200);

        assertEquals("event456", event.getId());
        assertEquals("Science Fair", event.getTitle());
        assertEquals("A local community science fair for all ages.", event.getDescription());
        assertEquals("user456", event.getOrganizerId());
        assertEquals(newEventDate, event.getStartDate());
        assertEquals(newLocation, event.getLocation());
        assertEquals("http://example.com/new_qrcode.jpg", event.getQrCode());
        assertEquals("http://example.com/new_poster.jpg", event.getEventPoster());
        assertEquals((Integer) 200, event.getAttendeeLimit());
    }
}