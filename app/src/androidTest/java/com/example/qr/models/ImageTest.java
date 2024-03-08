package com.example.qr.models;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.Date;

/**
 * Unit tests for the Image class.
 */
public class ImageTest {

    /**
     * Test the creation of an Image object.
     */
    @Test
    public void testImageCreation() {
        Date now = new Date();
        Image image = new Image("1", "http://example.com/image.jpg", "user123", now, "event456");

        assertEquals("1", image.getId());
        assertEquals("http://example.com/image.jpg", image.getUrl());
        assertEquals("user123", image.getUploadedBy());
        assertEquals(now, image.getUploadTime());
        assertEquals("event456", image.getRelatedTo());
    }

    /**
     *  Test the setters and getters for the Image class.
     */
    @Test
    public void testSettersAndGetters() {
        Image image = new Image("1", "http://example.com/image.jpg", "user123", new Date(), "event456");
        image.setUrl("http://example.com/new_image.jpg");
        image.setUploadedBy("user456");
        image.setUploadTime(new Date(0)); // Setting a specific time for testing
        image.setRelatedTo("event789");

        assertEquals("http://example.com/new_image.jpg", image.getUrl());
        assertEquals("user456", image.getUploadedBy());
        assertEquals(new Date(0), image.getUploadTime());
        assertEquals("event789", image.getRelatedTo());
    }
}
