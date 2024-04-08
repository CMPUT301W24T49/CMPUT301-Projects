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

/**
 * GuestHomeFragment displays the home screen for the guest user.
 */
public class GuestHomeFragment extends Fragment {

    @Override

    /**
     *
     * On create view
     *
     * @param inflater  the inflater.  It is NonNull
     * @param container  the container.
     * @param savedInstanceState  the saved instance state.
     * @return View
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_guest_home, container, false);

        // Find the attendee button by its ID
        Button attendeeButton = view.findViewById(R.id.button_attendee);

        // Find the organizer button by its ID
        Button organizerButton = view.findViewById(R.id.button_organizer);


        // Set a click listener for the attendee button
        attendeeButton.setOnClickListener(new View.OnClickListener() {
            @Override

            /**
             *
             * On click
             *
             * @param v  the v.
             */
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

        // Set a click listener for the attendee button
        organizerButton.setOnClickListener(new View.OnClickListener() {
            @Override

            /**
             *
             * On click
             *
             * @param v  the v.
             */
            public void onClick(View v) {

                // Replace the HomeFragment with AttendeeFragment
                OrganizerFragment organizerFragment = new OrganizerFragment();
                FragmentManager fragmentManager = getFragmentManager();
                if (fragmentManager != null) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, organizerFragment)
                            .addToBackStack(null) // This adds the transaction to the back stack
                            .commit();
                }
            }
        });

        return view;
    }
}
