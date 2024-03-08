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





        return view;
    }


}
