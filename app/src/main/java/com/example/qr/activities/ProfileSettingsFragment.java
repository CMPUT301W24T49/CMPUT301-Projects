package com.example.qr.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.fragment.app.DialogFragment;

import com.example.qr.R;

public class ProfileSettingsFragment extends DialogFragment {

    // default default constructor
    public ProfileSettingsFragment() {
    }

    // onCreateView method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_attendee_profile_settings, container, false);



        // Inflate the layout for this fragment
        return view;
    }
}
