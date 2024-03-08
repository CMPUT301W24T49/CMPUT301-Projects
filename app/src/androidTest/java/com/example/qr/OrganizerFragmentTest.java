package com.example.qr;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.qr.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class OrganizerFragmentTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testCreateNewButtonClick() {
        // Wait for the MainActivity to be started and create event to be displayed
        Espresso.onView(withId(R.id.button_organizer)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.create_events_btn)).perform(ViewActions.click());

        // Now check if the create event layout is displayed by checking one of its views
        Espresso.onView(withId(R.id.fragment_create_new_event)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testOrganzierEventsButtonClick() {
        // Wait for the MainActivity to be started and events screen to be displayed
        Espresso.onView(withId(R.id.button_organizer)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.events_btn)).perform(ViewActions.click());

        // Now check if the event list layout is displayed by checking one of its views
        Espresso.onView(withId(R.id.fragment_event_list_layout)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testUseExistingQrButtonClick() {
        // Wait for the MainActivity to be started and reuse QR screen to be displayed
        Espresso.onView(withId(R.id.button_organizer)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.create_events_btn)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.btnUseExistingQr)).perform(ViewActions.click());

        // Now check if the reuse QR code screen is displayed by checking one of its views
        Espresso.onView(withId(R.id.fragment_reuse_qr)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testProfileSettingsButtonClick() {
        // Wait for the MainActivity to be started and profileSettings to be displayed
        Espresso.onView(withId(R.id.button_organizer)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.settings_btn)).perform(ViewActions.click());
        Espresso.onView(withId(R.id.btnProfileSettings)).perform(ViewActions.click());

        // Now check if the profileSettings is displayed by checking one of its views
        Espresso.onView(withId(R.id.profile_settings_layout)).check(ViewAssertions.matches(isDisplayed()));
    }
}
