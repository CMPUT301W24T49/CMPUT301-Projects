// Adapted from Bashar's AttendeeFragment.java
package com.example.qr.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qr.R;


/**
 * The class Organizer fragment extends fragment
 */
public class OrganizerFragment extends Fragment {


    /**
     *
     * Organizer fragment
     *
     * @return public
     */
    public OrganizerFragment() {

        // Required empty public constructor
    }


    /**
     *
     * On create view
     *
     * @param inflater  the inflater.
     * @param container  the container.
     * @param savedInstanceState  the saved instance state.
     * @return View
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_organizer, container, false);

        // Button initialization
        Button createEventsBtn = view.findViewById(R.id.btn_create_events);
        Button eventsBtn = view.findViewById(R.id.btn_my_events);
        Button settingsBtn = view.findViewById(R.id.btn_settings);
        Button btnClose = view.findViewById(R.id.btn_close);

        // Create events button onclick listener
        createEventsBtn.setOnClickListener(v -> {
            OrganizerCreateEventFragment organizerCreateEvent = new OrganizerCreateEventFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, organizerCreateEvent)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });

        // My events button onclick listener
        eventsBtn.setOnClickListener(v -> {
            OrganizerEventListFragment organizerEventList = new OrganizerEventListFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, organizerEventList)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });

        // Settings button onclick listener
        settingsBtn.setOnClickListener(v -> {
            AttendeeSettingsFragment attendeeSettings = new AttendeeSettingsFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, attendeeSettings)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });

        // Close button going back previous screen
        btnClose.setOnClickListener(v -> {
            // Check if fragment is added to an activity and if activity has a FragmentManager
            if (isAdded() && getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }


}
