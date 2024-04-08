package com.example.qr.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.content.SharedPreferences;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.qr.R;
import com.example.qr.models.SharedViewModel;

public class AttendeeSettingsFragment extends DialogFragment {

        private SharedPreferences sharedPreferences;

        // public default constructor
        public AttendeeSettingsFragment() {
        }

        // onCreateView method
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_attendee_settings, container, false);

            Button btnClose = view.findViewById(R.id.btn_close);
            Button btnProfile = view.findViewById(R.id.btnProfileSettings);
            Button btnExitToMain = view.findViewById(R.id.btnExitToMainMenu);
            Switch switchLocation = view.findViewById(R.id.switchGeoTracking);

            // profile settings
            btnProfile.setOnClickListener(v -> {
                AttendeeProfileSettingsFragment profileSettingsFragment = new AttendeeProfileSettingsFragment();
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, profileSettingsFragment)
                            .addToBackStack(null)  // Optional: Add transaction to back stack
                            .commit();
                }
            });

            /*
            // Microsoft, 2024-03-07, Github Co-Pilot,
            // prompt: "when i toggle the switch and leave the screen and comeback,
            // the switch is toggled back off"
            */
            // ******* start of copilot code ******* //
            sharedPreferences = getActivity().getSharedPreferences("AttendeeSettings", 0); // 0 for private mode

            // Get the saved state of the switches
            boolean isNotificationsOn = sharedPreferences.getBoolean("Notifications", false);
            boolean isLocationOn = sharedPreferences.getBoolean("Location", false);

            
            SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
            viewModel.setOrganizerNotificationStatus(isNotificationsOn);

            switchLocation.setOnCheckedChangeListener((buttonView, isChecked) -> {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("Location", isChecked);
                editor.apply();
            });
            // ******* end of copilot code ******* //

            btnClose.setOnClickListener(v -> {
                if (isAdded() && getActivity() != null) {
                    getActivity().onBackPressed();
                }
            });

            // exit to fragment_main
            btnExitToMain.setOnClickListener(v -> {
                GuestHomeFragment guestHomeFragment = new GuestHomeFragment();
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, guestHomeFragment)
                            .addToBackStack(null)  // Optional: Add transaction to back stack
                            .commit();
                }
            });



            return view;

        }
}
