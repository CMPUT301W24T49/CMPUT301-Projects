package com.example.qr.fragments;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.qr.activities.MainActivity;
import com.example.qr.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

@RunWith(AndroidJUnit4.class)
public class AdministratorFragmentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testProfileOptionButtonClick() {
        // Navigate to AdministratorFragment first
        Espresso.onView(withId(R.id.button_admin)).perform(ViewActions.click());

        // Click on the Profile Option button
        Espresso.onView(withId(R.id.btn_profile_option)).perform(ViewActions.click());

        // Check if ProfileListFragment is displayed
        Espresso.onView(withId(R.id.fragment_profile_list)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testEventsOptionButtonClick() {
        // Navigate to AdministratorFragment first
        Espresso.onView(withId(R.id.button_admin)).perform(ViewActions.click());

        // Click on the Events Option button
        Espresso.onView(withId(R.id.btn_events_option)).perform(ViewActions.click());

        // Check if EventListFragment is displayed
        Espresso.onView(withId(R.id.fragment_event_list_layout)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testImagesOptionButtonClick() {
        // Navigate to AdministratorFragment first
        Espresso.onView(withId(R.id.button_admin)).perform(ViewActions.click());

        // Click on the Images Option button
        Espresso.onView(withId(R.id.btn_images_option)).perform(ViewActions.click());

        // Check if ImageListFragment is displayed
        Espresso.onView(withId(R.id.fragment_image_list_layout)).check(ViewAssertions.matches(isDisplayed()));
    }

    // Add more tests for other buttons as needed

}
