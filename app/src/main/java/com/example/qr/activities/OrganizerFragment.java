package com.example.qr.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.qr.R;

public class OrganizerFragment extends Fragment {

    public OrganizerFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_organizer, container, false);

        Button createEventsBtn = view.findViewById(R.id.create_events_btn);
        Button eventsBtn = view.findViewById(R.id.events_btn);
        Button settingsBtn = view.findViewById(R.id.settings_btn);
        Button closeBtn = view.findViewById(R.id.close_btn);


        // Create events button onclick listener
        createEventsBtn.setOnClickListener(v -> {
            CreateEventFragment createEventFragment = new CreateEventFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, createEventFragment)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });

        // Events button onclick listener
        eventsBtn.setOnClickListener(v -> {
            EventListFragment eventListFragment = new EventListFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, eventListFragment)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });

        // Settings button onclick listener
        settingsBtn.setOnClickListener(v -> {
            OrganizerSettingsFragment organizerSettingsFragment = new OrganizerSettingsFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, organizerSettingsFragment)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });

        // Close button to go back to previous screen
        closeBtn.setOnClickListener(v -> {
            // Check if the fragment is added to an activity and if the activity has a FragmentManager
            if (isAdded() && getActivity() != null) {
                getActivity().onBackPressed();
            }
        });




        return view;
    }


}
