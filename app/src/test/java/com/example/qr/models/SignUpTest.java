package com.example.qr.models;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SignUpTest {
    private SignUp signUp;
    private String eventId = "eventId";
    private String userId = "userId";

    @Before
    public void setUp() {
        signUp = new SignUp(eventId, userId, null, null);
    }

    @Test
    public void testGetEventId() {
        assertEquals(eventId, signUp.getEventId());
    }

    @Test
    public void testGetUserId() {
        assertEquals(userId, signUp.getUserId());
    }

    @Test
    public void testSignUpNotNull() {
        assertNotNull(signUp);
    }
}