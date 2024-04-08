package com.example.qr.fragments;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.example.qr.models.CheckIn;
import com.example.qr.models.Event;
import com.example.qr.models.SignUp;
import com.example.qr.models.User;
import com.example.qr.utils.FirebaseUtil;
import com.example.qr.utils.FirebaseUtil.OnCollectionFetchedListener;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;
import java.util.UUID;

/**
 * Unit tests for FirebaseUtil class.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FirebaseUtilTest {

    final String eventId = "testeven1234";
    final String userId = "testuser1234";


    /**
     * Test for adding and fetching events.
     */
    @Test
    public void test1AddAndFetchEvent() {

        Event mockEvent = new Event(eventId, "eventTitle", "eventDescription", "organizerId", null, null, "startTime", "endTime", null, "qrCode", "qrpcode", "eventPoster", 100);

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
     * Test for adding and fetching check-ins.
     */
    @Test
    public void test2AddAndFetchCheckIn() {

        CheckIn mockCheckIn = new CheckIn(eventId, "userId", null, null);

        // Add a check-in
        FirebaseUtil.addCheckIn(mockCheckIn, documentReference -> {

            // Now fetch check-ins to verify
            FirebaseUtil.fetchCollection("CheckIn", CheckIn.class, new OnCollectionFetchedListener<CheckIn>() {
                @Override
                public void onCollectionFetched(List<CheckIn> checkInList) {
                    // Verify the check-in is in the fetched list
                    boolean isCheckInPresent = false;
                    for (CheckIn checkIn : checkInList) {
                        if (checkIn.getUserId().equals(mockCheckIn.getUserId())) {
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

    /**
     * Test for SignUp
     */
    @Test
    public void test3AddAndFetchSignUp() {
        SignUp mockSignUp = new SignUp(eventId, "userId", null, null);

        // Add a sign-up
        FirebaseUtil.addSignUp(mockSignUp, documentReference -> {
            // Now fetch sign-ups to verify
            FirebaseUtil.fetchCollection("SignUp", SignUp.class, new OnCollectionFetchedListener<SignUp>() {
                @Override
                public void onCollectionFetched(List<SignUp> signUpList) {
                    // Verify the sign-up is in the fetched list
                    boolean isSignUpPresent = false;
                    for (SignUp signUp : signUpList) {
                        if (signUp.getUserId().equals(mockSignUp.getUserId())) {
                            isSignUpPresent = true;
                            break;
                        }
                    }
                    assertTrue("The added sign-up should be present in the fetched list", isSignUpPresent);
                }

                @Override
                public void onError(Exception e) {
                    fail("Fetching sign-ups failed");
                }
            });
        }, e -> {
            fail("Adding sign-up failed");
        });
    }


    /**
     * Test for deleting an event - this will delete all signups and checkups related to this event too
     */
    @Test
    public void test4DeleteEvent() {
        FirebaseUtil.deleteEvent(eventId, aVoid -> {
            // Now fetch events to verify
            FirebaseUtil.fetchCollection("Events", Event.class, new OnCollectionFetchedListener<Event>() {
                @Override
                public void onCollectionFetched(List<Event> eventList) {
                    // Verify the event is not in the fetched list
                    boolean isEventPresent = false;
                    for (Event event : eventList) {
                        if (event.getId().equals(eventId)) {
                            isEventPresent = true;
                            break;
                        }
                    }
                    assertFalse("The deleted event should not be present in the fetched list", isEventPresent);
                }

                @Override
                public void onError(Exception e) {
                    fail("Fetching events failed");
                }
            });
        }, e -> {
            fail("Deleting event failed");
        });
    }

    /**
     * Test for adding and fetching users.
     */
    @Test
    public void test5AddAndFetchUser() {

        User mockUser = new User(userId, "firstName", "lastName", "userRole", "userProfilePicture", "userPhoneNumber", "userEmail", "userHomepage");

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


    @Test
    public void test6DeleteUser() {
        FirebaseUtil.deleteUser(userId, aVoid -> {
            // Now fetch users to verify
            FirebaseUtil.fetchCollection("Users", User.class, new OnCollectionFetchedListener<User>() {
                @Override
                public void onCollectionFetched(List<User> userList) {
                    // Verify the user is not in the fetched list
                    boolean isUserPresent = false;
                    for (User user : userList) {
                        if (user.getId().equals(userId)) {
                            isUserPresent = true;
                            break;
                        }
                    }
                    assertFalse("The deleted user should not be present in the fetched list", isUserPresent);
                }

                @Override
                public void onError(Exception e) {
                    fail("Fetching users failed");
                }
            });
        }, e -> {
            fail("Deleting user failed");
        });
    }

}
