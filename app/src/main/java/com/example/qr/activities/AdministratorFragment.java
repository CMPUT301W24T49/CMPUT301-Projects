package com.example.qr.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.qr.R;
/**
 * AdministratorFragment handles the UI for the admin dashboard, providing navigation to different admin functionalities.
 */
public class AdministratorFragment extends Fragment {


    /**
     *
     * Administrator fragment
     *
     * @return public
     */
    public AdministratorFragment() {

        // Required empty public constructor
    }

    @Override


/**
 *
 * On create view
 *
 * @param inflater  the inflater.
 * @param container  the container.
 * @param savedInstanceState  the saved instance state.
 * @return View
 */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_administrator, container, false);

        // Initialize the buttons
        Button btnEventsOption = view.findViewById(R.id.btn_events_option);
        Button btnProfileOption = view.findViewById(R.id.btn_profile_option);
        Button btnImagesOption = view.findViewById(R.id.btn_images_option);
        Button btnSettings = view.findViewById(R.id.btn_settings);
        Button btnClose = view.findViewById(R.id.btnClose);

        // Set up the button click listeners
        // Replace these with actual implementations
        btnProfileOption.setOnClickListener(v -> {
            Fragment profileListFragment = new ProfileListFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, profileListFragment)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });


        btnEventsOption.setOnClickListener(v -> {
            Fragment eventListFragment = new EventListFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, eventListFragment)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });

        btnImagesOption.setOnClickListener(v -> {
            Fragment imageListFragment = new ImageListFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, imageListFragment)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });

        btnSettings.setOnClickListener(v -> {
            // Handle Settings click
            AdminSettingsFragment adminSettingsFragment = new AdminSettingsFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, adminSettingsFragment)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });

        // Close button to go back to the previous screen
        btnClose.setOnClickListener(v -> {
            // Check if the fragment is added to an activity and if the activity has a FragmentManager
            if (isAdded() && getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }
}
