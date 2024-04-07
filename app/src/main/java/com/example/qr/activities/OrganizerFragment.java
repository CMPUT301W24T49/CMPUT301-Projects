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

public class OrganizerFragment extends Fragment {

    public OrganizerFragment() {
        // Required empty public constructor
    }

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
            OrganizerCreateEventFragment organizerCreateEventFragment = new OrganizerCreateEventFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, organizerCreateEventFragment)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });

        // Events button onclick listener
        eventsBtn.setOnClickListener(v -> {
            OrganizerEventListFragment eventListOrganizerFragment = new OrganizerEventListFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, eventListOrganizerFragment)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });

        // Settings button onclick listener
        settingsBtn.setOnClickListener(v -> {
            AttendeeSettingsFragment attendeeSettingsFragment = new AttendeeSettingsFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, attendeeSettingsFragment)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });

        btnClose.setOnClickListener(v -> {
            if (isAdded() && getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }


}
