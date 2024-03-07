package com.example.qr.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.qr.R;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Find the admin button by its ID
        Button adminButton = view.findViewById(R.id.button_admin);

        // Find the attendee button by its ID
        Button attendeeButton = view.findViewById(R.id.button_attendee);

        // Set a click listener for the admin button
        adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the HomeFragment with AdminMenuFragment
                AdministratorFragment adminMenuFragment = new AdministratorFragment();
                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, adminMenuFragment)
                            .addToBackStack(null) // This adds the transaction to the back stack
                            .commit();
                }
            }
        });

        // Set a click listener for the attendee button
        attendeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the HomeFragment with AttendeeFragment
                AttendeeFragment attendeeFragment = new AttendeeFragment();
                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, attendeeFragment)
                            .addToBackStack(null) // This adds the transaction to the back stack
                            .commit();
                }
            }
        });

        return view;
    }
}
