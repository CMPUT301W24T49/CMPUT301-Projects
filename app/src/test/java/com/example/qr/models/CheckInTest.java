package com.example.qr.models;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import com.google.firebase.firestore.GeoPoint;
import java.util.Date;

/**
 * Unit tests for the CheckIn class.
 */
public class CheckInTest {

    /**
     * Test the creation of a CheckIn object.
     */
    @Test
    public void testCheckInCreation() {
        GeoPoint location = new GeoPoint(37.7749, -122.4194); // Example coordinates for San Francisco
        Date checkInTime = new Date();
        CheckIn checkIn = new CheckIn("event789", "user123", checkInTime, location);

        assertEquals("event789", checkIn.getEventId());
        assertEquals("user123", checkIn.getUserId());
        assertEquals(checkInTime, checkIn.getCheckInTime());
        assertEquals(location, checkIn.getLocation());
    }

    /**
     *  Test the setters and getters for the CheckIn class.
     */
    @Test
    public void testSettersAndGetters() {
        CheckIn checkIn = new CheckIn();
        checkIn.setEventId("event1011");
        checkIn.setUserId("user456");

        Date newCheckInTime = new Date();
        checkIn.setCheckInTime(newCheckInTime);

        GeoPoint newLocation = new GeoPoint(40.7128, -74.0060); // Example coordinates for New York City
        checkIn.setLocation(newLocation);

        assertEquals("event1011", checkIn.getEventId());
        assertEquals("user456", checkIn.getUserId());
        assertEquals(newCheckInTime, checkIn.getCheckInTime());
        assertEquals(newLocation, checkIn.getLocation());
    }
}

