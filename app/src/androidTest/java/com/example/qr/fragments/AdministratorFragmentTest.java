package com.example.qr.fragments;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
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

    /**
     * Tests the navigation to ProfileListFragment upon clicking the Profile Option button.
     * Verifies that the ProfileListFragment is displayed by clicking on the profile option button
     * from the AdministratorFragment.
     */
    @Test
    public void testProfileOptionButtonClick() {
        // Navigate to AdministratorFragment first
        Espresso.onView(withId(R.id.button_admin)).perform(ViewActions.click());

        // Click on the Profile Option button
        Espresso.onView(withId(R.id.btn_profile_option)).perform(ViewActions.click());

        // Check if ProfileListFragment is displayed
        Espresso.onView(withId(R.id.fragment_profile_list)).check(ViewAssertions.matches(isDisplayed()));
    }

    /**
     * Tests the navigation to EventListFragment upon clicking the Events Option button.
     * Verifies that the EventListFragment is displayed by clicking on the events option button
     * from the AdministratorFragment.
     */
    @Test
    public void testEventsOptionButtonClick() {
        // Navigate to AdministratorFragment first
        Espresso.onView(withId(R.id.button_admin)).perform(ViewActions.click());

        // Click on the Events Option button
        Espresso.onView(withId(R.id.btn_events_option)).perform(ViewActions.click());

        // Check if EventListFragment is displayed
        Espresso.onView(withId(R.id.fragment_event_list_layout)).check(ViewAssertions.matches(isDisplayed()));
    }

    /**
     * Tests the navigation to ImageListFragment upon clicking the Images Option button.
     * Verifies that the ImageListFragment is displayed by clicking on the images option button
     * from the AdministratorFragment.
     */
    @Test
    public void testImagesOptionButtonClick() {
        // Navigate to AdministratorFragment first
        Espresso.onView(withId(R.id.button_admin)).perform(ViewActions.click());

        // Click on the Images Option button
        Espresso.onView(withId(R.id.btn_images_option)).perform(ViewActions.click());

        // Check if ImageListFragment is displayed
        Espresso.onView(withId(R.id.fragment_image_list_layout)).check(ViewAssertions.matches(isDisplayed()));
    }

    /**
     * Tests the navigation to the Settings page upon clicking the Setting Option button.
     * Verifies that the Settings page is displayed by clicking on the setting option button
     * from the AdministratorFragment.
     */
    @Test
    public void testSettingOptionButtonClick() {
        // Navigate to AdministratorFragment first
        Espresso.onView(withId(R.id.button_admin)).perform(ViewActions.click());

        // Click on the Setting Option button
        Espresso.onView(withId(R.id.btn_settings)).perform(ViewActions.click());

        // Check if Setting page is displayed
        Espresso.onView(withId(R.id.fragment_admin_settings)).check(ViewAssertions.matches(isDisplayed()));
    }

}
