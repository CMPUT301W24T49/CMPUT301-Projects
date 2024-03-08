package com.example.qr;

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

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
public class AttendeeFragmentTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testAttendeeEventsButtonClick() {
        // Wait for the MainActivity to be started and HomeFragment to be displayed
        Espresso.onView(withId(R.id.button_attendee)).perform(ViewActions.click());

        // Now check if the AttendeeFragment is displayed by checking one of its views
        Espresso.onView(withId(R.id.fragment_attendee_layout)).check(ViewAssertions.matches(isDisplayed()));

        // Go to the event list
        Espresso.onView(withId(R.id.btnEvents)).perform(ViewActions.click());

        // Now check if the EventListFragment is displayed by checking one of its views
        Espresso.onView(withId(R.id.fragment_event_list_layout)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testAttendeeSettingsButtonClick() {
        // Wait for the MainActivity to be started and HomeFragment to be displayed
        Espresso.onView(withId(R.id.button_attendee)).perform(ViewActions.click());

        // Now check if the AttendeeFragment is displayed by checking one of its views
        Espresso.onView(withId(R.id.fragment_attendee_layout)).check(ViewAssertions.matches(isDisplayed()));

        // Go to settings
        Espresso.onView(withId(R.id.btnSettings)).perform(ViewActions.click());

        // Now check if the OrganizerSettingsFragment is displayed by checking one of its views
        Espresso.onView(withId(R.id.fragment_settings_layout)).check(ViewAssertions.matches(isDisplayed()));
    }



}