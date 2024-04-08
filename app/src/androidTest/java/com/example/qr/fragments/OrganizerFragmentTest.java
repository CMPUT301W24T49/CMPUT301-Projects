package com.example.qr.fragments;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.qr.R;
import com.example.qr.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class OrganizerFragmentTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testOrganzierEventsButtonClick() {
        try {
            Thread.sleep(3000); // Sleep for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Wait for the MainActivity to be started and events screen to be displayed
        Espresso.onView(withId(R.id.button_organizer)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.btn_my_events)).perform(ViewActions.click());

        try {
            Thread.sleep(3000); // Sleep for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Now check if the event list layout is displayed by checking one of its views
        Espresso.onView(withId(R.id.fragment_event_list_layout)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testCreateQrButtonClick() {
        try {
            Thread.sleep(3000); // Sleep for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Wait for the MainActivity to be started and reuse QR screen to be displayed
        Espresso.onView(withId(R.id.button_organizer)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.btn_create_events)).perform(ViewActions.click());
        onView(withId(R.id.eventTitle)).perform(ViewActions.typeText("Test Create Event"));
        onView(withId(R.id.eventLocation)).perform(ViewActions.typeText("Edmonton"),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.eventDescription)).perform(ViewActions.typeText("Test Description"),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.startDate)).perform(ViewActions.typeText("12/12/2024"),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.endDate)).perform(ViewActions.typeText("12/13/2024"),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.startTime)).perform(ViewActions.typeText("00:00"),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.endTime)).perform(ViewActions.typeText("23:59"),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.maxAttendees)).perform(ViewActions.typeText("100"),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnGenerateQr)).perform(click());
        try {
            Thread.sleep(3000); // Sleep for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Espresso.onView(withId(R.id.fragment_organizer_layout)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testUseExistingQrButtonClick() {
        try {
            Thread.sleep(3000); // Sleep for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Wait for the MainActivity to be started and reuse QR screen to be displayed
        Espresso.onView(withId(R.id.button_organizer)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.btn_create_events)).perform(ViewActions.click());
        onView(withId(R.id.eventTitle)).perform(ViewActions.typeText("Test Reuse QR"));
        onView(withId(R.id.eventLocation)).perform(ViewActions.typeText("Edmonton"),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.eventDescription)).perform(ViewActions.typeText("Test Description"),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.startDate)).perform(ViewActions.typeText("12/12/2024"),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.endDate)).perform(ViewActions.typeText("12/13/2024"),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.startTime)).perform(ViewActions.typeText("00:00"),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.endTime)).perform(ViewActions.typeText("23:59"),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.maxAttendees)).perform(ViewActions.typeText("100"),
                ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnUseExistingQr)).perform(click());
        try {
            Thread.sleep(3000); // Sleep for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Now check if the reuse QR code screen is displayed by checking one of its views
        Espresso.onView(withId(R.id.fragment_reuse_qr)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testProfileSettingsButtonClick() {
        try {
            Thread.sleep(3000); // Sleep for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Wait for the MainActivity to be started and profileSettings to be displayed
        Espresso.onView(withId(R.id.button_organizer)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.btn_settings)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.btnProfileSettings)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.btn_close)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.btnExitToMainMenu)).perform(ViewActions.click());
        try {
            Thread.sleep(3000); // Sleep for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Now check if the profileSettings is displayed by checking one of its views
        Espresso.onView(withId(R.id.default_layout)).check(ViewAssertions.matches(isDisplayed()));
    }
}
