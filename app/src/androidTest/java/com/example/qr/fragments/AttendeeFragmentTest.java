package com.example.qr.fragments;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static org.hamcrest.Matchers.allOf;
import com.example.qr.R;
import com.example.qr.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.app.Instrumentation;
import android.content.Intent;
import android.provider.MediaStore;

import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import android.app.Activity;
import android.app.Instrumentation;
import android.provider.MediaStore;
import androidx.test.espresso.intent.Intents;
import org.junit.After;
import org.junit.Before;
import androidx.test.rule.GrantPermissionRule;
import android.Manifest;

@RunWith(AndroidJUnit4.class)
public class AttendeeFragmentTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule grantCameraPermission = GrantPermissionRule.grant(Manifest.permission.CAMERA);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }
    @Test
    public void testAttendeeEventsButtonClick() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Wait for the MainActivity to be started and HomeFragment to be displayed
        Espresso.onView(withId(R.id.button_attendee)).perform(ViewActions.click());

        // Now check if the AttendeeFragment is displayed by checking one of its views
        Espresso.onView(withId(R.id.fragment_attendee_layout)).check(ViewAssertions.matches(isDisplayed()));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Go to the event list
        Espresso.onView(withId(R.id.btnMyEvents)).perform(ViewActions.click());

        // Now check if the EventListFragment is displayed by checking one of its views
        Espresso.onView(withId(R.id.fragment_event_list_layout)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testAttendeeSettingsButtonClick() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Wait for the MainActivity to be started and HomeFragment to be displayed
        Espresso.onView(withId(R.id.button_attendee)).perform(ViewActions.click());

        // Now check if the AttendeeFragment is displayed by checking one of its views
        Espresso.onView(withId(R.id.fragment_attendee_layout)).check(ViewAssertions.matches(isDisplayed()));
        try {
            Thread.sleep(2000); // Sleep for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Go to settings
        Espresso.onView(withId(R.id.btnSettings)).perform(ViewActions.click());

        // Now check if the OrganizerSettingsFragment is displayed by checking one of its views
        Espresso.onView(withId(R.id.fragment_settings_layout)).check(ViewAssertions.matches(isDisplayed()));
    }


    @Test
    public void testAttendeeBrowseEventsButtonClick() {

        // Wait for the MainActivity to be started and HomeFragment to be displayed
        Espresso.onView(withId(R.id.button_attendee)).perform(ViewActions.click());

        try {
            Thread.sleep(2000); // Sleep for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Now check if the AttendeeFragment is displayed by checking one of its views
        Espresso.onView(withId(R.id.fragment_attendee_layout)).check(ViewAssertions.matches(isDisplayed()));

        // Go to browse events
        Espresso.onView(withId(R.id.btnBrowseEvents)).perform(ViewActions.click());

        try {
            Thread.sleep(2000); // Sleep for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Now check if the AttendeeBrowseEventListFragment is displayed by checking one of its views
        Espresso.onView(withId(R.id.fragment_event_list_layout)).check(ViewAssertions.matches(isDisplayed()));
    }


}