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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_organizer, container, false);
        
        // Buttons initialization
        Button btnCreateEvents = view.findViewById(R.id.btn_create_events);
        Button btnMyEvents = view.findViewById(R.id.btn_my_events);
        Button btnSettings = view.findViewById(R.id.btn_settings);
        Button btnClose = view.findViewById(R.id.btn_close);
        
        // Create Events button
        btnCreateEvents.setOnClickListener(v -> {
            CreateEventFragment createEventFragment = new CreateEventFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, createEventFragment)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });

        // My Events Button
        btnMyEvents.setOnClickListener(v -> {
            OrganizerEventListFragment organizerEventListFragment = new OrganizerEventListFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, organizerEventListFragment)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });


        // Settings Button
        btnSettings.setOnClickListener(v -> {
            AttendeeSettingsFragment attendeeSettingsFragment = new AttendeeSettingsFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, attendeeSettingsFragment)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });

        // citation: OpenAI, ChatGPT 4, 2024: How do I click a button to change fragments
        // in android studio
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new instance of OrganizerFragment
                HomeFragment homeFragment = new HomeFragment();

                // Perform the fragment transaction
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager(); // Use getFragmentManager() in a Fragment
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with OrganizerFragment. Assume R.id.fragment_container is the ID of your FrameLayout
                fragmentTransaction.replace(R.id.fragment_container, homeFragment);
                // fragmentTransaction.addToBackStack(null); // Optional: Add this transaction to the back stack
                fragmentTransaction.commit(); // Commit the transaction
            }
        });
        // end citation

        return view;
    }


}
