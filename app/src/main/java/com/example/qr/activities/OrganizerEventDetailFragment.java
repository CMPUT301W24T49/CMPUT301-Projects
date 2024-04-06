package com.example.qr.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.qr.R;
import com.example.qr.models.Event;

public class OrganizerEventDetailFragment extends Fragment {

    public Event event;

    public OrganizerEventDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate layout
        View view = inflater.inflate(R.layout.fragment_organizer_event_detail, container, false);
        event = (Event) getArguments().getSerializable("Event");

        // Initialize views
        ImageView profile = view.findViewById(R.id.ivEventPoster);
        TextView title = view.findViewById(R.id.eventTitle);
        TextView description = view.findViewById(R.id.eventDesc);
        TextView location = view.findViewById(R.id.eventLocation);
        TextView startDate = view.findViewById(R.id.startDate);
        TextView endDate = view.findViewById(R.id.endDate);
        TextView startTime = view.findViewById(R.id.startTime);
        TextView endTime = view.findViewById(R.id.endTime);
        TextView maxAttendees = view.findViewById(R.id.maxAttendees);
        ImageView qrCode = view.findViewById(R.id.ivQrCode);
        Button btnAnnouncements = view.findViewById(R.id.btnAnnouncements);
        Button btnAttendeeList = view.findViewById(R.id.btnAttendeeList);
        Button btnSignupList = view.findViewById(R.id.btnSignupList);
        
        
//        if(event.getEventPoster() != null) {
//            Glide.with(getContext()).load(event.getEventPoster()).into(profile);
//        }
        
        if(event.getId() != null) {
            String titleText = "Title: " + event.getTitle();
            title.setText(titleText);
        }

//        btnAttendeeList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        
        return view;

    }
}