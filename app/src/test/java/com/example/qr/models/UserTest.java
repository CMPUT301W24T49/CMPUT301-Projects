package com.example.qr.models;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

/**
 * Unit tests for the User class.
 */
public class UserTest {

    /**
     * Test the creation of a User object.
     */
    @Test
    public void testUserCreation() {
        Map<String, String> contactInfo = new HashMap<>();
        contactInfo.put("email", "user@example.com");
        contactInfo.put("phone", "1234567890");

        User user = new User("user123", "John Doe", "attendee", "http://example.com/profile.jpg", contactInfo, "http://example.com");

        assertEquals("user123", user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("attendee", user.getRole());
        assertEquals("http://example.com/profile.jpg", user.getProfilePicture());
        assertEquals(contactInfo, user.getContactInfo());
        assertEquals("http://example.com", user.getHomepage());
    }

    /**
     *  Test the setters and getters for the User class.
     */
    @Test
    public void testSettersAndGetters() {
        User user = new User();
        user.setId("user456");
        user.setName("Jane Doe");
        user.setRole("organizer");
        user.setProfilePicture("http://example.com/new_profile.jpg");

        Map<String, String> newContactInfo = new HashMap<>();
        newContactInfo.put("email", "jane@example.com");
        user.setContactInfo(newContactInfo);

        user.setHomepage("http://example.com/jane");

        assertEquals("user456", user.getId());
        assertEquals("Jane Doe", user.getName());
        assertEquals("organizer", user.getRole());
        assertEquals("http://example.com/new_profile.jpg", user.getProfilePicture());
        assertEquals(newContactInfo, user.getContactInfo());
        assertEquals("http://example.com/jane", user.getHomepage());
    }
}

