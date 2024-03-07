package com.example.qr.activities;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.qr.R;

public class AttendeeFragment extends Fragment {

    public AttendeeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_attendee, container, false);

        Button btnClose = view.findViewById(R.id.btn_close);
        Button btnEvents = view.findViewById(R.id.btnEvents);

        btnEvents.setOnClickListener(v -> {
            EventListFragment eventListFragment = new EventListFragment();
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, eventListFragment)
                        .addToBackStack(null)  // Optional: Add transaction to back stack
                        .commit();
            }
        });

        btnClose.setOnClickListener(v -> {
            if (isAdded() && getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}
