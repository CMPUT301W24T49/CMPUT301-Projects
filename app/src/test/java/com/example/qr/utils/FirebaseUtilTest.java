package com.example.qr.utils;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.example.qr.models.CheckIn;
import com.example.qr.models.Event;
import com.example.qr.models.User;
import com.example.qr.utils.FirebaseUtil.OnCollectionFetchedListener;

import org.junit.Test;

import java.util.List;
import java.util.UUID;

/**
 * Unit tests for FirebaseUtil class.
 */
public class FirebaseUtilTest {

    /**
     * Test for adding and fetching events.
     */
    @Test
    public void testAddAndFetchEvent() {

        String id = UUID.randomUUID().toString();

        Event mockEvent = new Event(id, "eventTitle", "eventDescription", "organizerId", null, null, "qrCode", "eventPoster", 100);

        // Add an event
        FirebaseUtil.addEvent(mockEvent, documentReference -> {

            // Now fetch events to verify
            FirebaseUtil.fetchCollection("Events", Event.class, new OnCollectionFetchedListener<Event>() {
                @Override
                public void onCollectionFetched(List<Event> eventList) {
                    // Verify the event is in the fetched list
                    boolean isEventPresent = false;
                    for (Event event : eventList) {
                        if (event.getId().equals(mockEvent.getId())) {
                            isEventPresent = true;
                            break;
                        }
                    }
                    assertTrue("The added event should be present in the fetched list", isEventPresent);
                }

                @Override
                public void onError(Exception e) {
                    fail("Fetching events failed");
                }
            });
        }, e -> {
            fail("Adding event failed");
        });
    }

    /**
     * Test for adding and fetching users.
     */
    @Test
    public void testAddAndFetchUser() {

        String id = UUID.randomUUID().toString();

        User mockUser = new User(id, "name", "role", "profilePicture", null, "homepage");

        // Add a user
        FirebaseUtil.addUser(mockUser, documentReference -> {

            // Now fetch users to verify
            FirebaseUtil.fetchCollection("Users", User.class, new OnCollectionFetchedListener<User>() {
                @Override
                public void onCollectionFetched(List<User> userList) {
                    // Verify the user is in the fetched list
                    boolean isUserPresent = false;
                    for (User user : userList) {
                        if (user.getId().equals(mockUser.getId())) {
                            isUserPresent = true;
                            break;
                        }
                    }
                    assertTrue("The added user should be present in the fetched list", isUserPresent);
                }

                @Override
                public void onError(Exception e) {
                    fail("Fetching users failed");
                }
            });
        }, e -> {
            fail("Adding user failed");
        });
    }

    /**
     * Test for adding and fetching check-ins.
     */
    @Test
    public void testAddAndFetchCheckIn() {

        String id = UUID.randomUUID().toString();

        CheckIn mockCheckIn = new CheckIn(id, "eventId", "userId", null, null);

        // Add a check-in
        FirebaseUtil.addCheckIn(mockCheckIn, documentReference -> {

            // Now fetch check-ins to verify
            FirebaseUtil.fetchCollection("CheckIns", CheckIn.class, new OnCollectionFetchedListener<CheckIn>() {
                @Override
                public void onCollectionFetched(List<CheckIn> checkInList) {
                    // Verify the check-in is in the fetched list
                    boolean isCheckInPresent = false;
                    for (CheckIn checkIn : checkInList) {
                        if (checkIn.getId().equals(mockCheckIn.getId())) {
                            isCheckInPresent = true;
                            break;
                        }
                    }
                    assertTrue("The added check-in should be present in the fetched list", isCheckInPresent);
                }

                @Override
                public void onError(Exception e) {
                    fail("Fetching check-ins failed");
                }
            });
        }, e -> {
            fail("Adding check-in failed");
        });
    }

}
