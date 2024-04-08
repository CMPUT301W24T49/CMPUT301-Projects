package com.example.qr.activities;

import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.qr.R;
import com.example.qr.models.Event;
import com.example.qr.utils.GenerateQRCode;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

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
        Button btnNotification = view.findViewById(R.id.btnNotifications);
        Button btnCheckInList = view.findViewById(R.id.btnCheckInList);
        Button btnSignupList = view.findViewById(R.id.btnSignupList);
        Button btnClose = view.findViewById(R.id.btn_close);
        

        // Load event poster image
        if(event.getEventPoster() != "") {
            Glide.with(getContext()).load(event.getEventPoster()).into(profile);
        }

        // Set event title
        if(event.getId() != null) {
            String titleText = "Title: " + event.getTitle();
            title.setText(titleText);
        }

        // Set event description
        if(event.getDescription() != null){
            String descText = "Description: " + event.getDescription();
            description.setText(descText);
        }

        // Set location; Convert geopoint into an actual address
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(event.getLocation().getLatitude(), event.getLocation().getLongitude(), 1);
            if(addresses != null) {
                String address = addresses.get(0).getAddressLine(0);
                String locationText = "Location: " + address;
                location.setText(locationText);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Set start date
        if(event.getStartDate() != null){
            String startDateText = "Start Date: " + android.text.format.DateFormat.format("dd-MM-yyyy", event.getStartDate());
            startDate.setText(startDateText);
        }

        // Set end date
        if(event.getEndDate() != null){
            String endDateText = "End Date: " + android.text.format.DateFormat.format("dd-MM-yyyy", event.getEndDate());
            endDate.setText(endDateText);
        }

        // Set start time
        if(!event.getStartTime().isEmpty()){
            String startTimeText = "Start Time: " + event.getStartTime();
            startTime.setText(startTimeText);
        }

        // Set end time
        if(!event.getEndTime().isEmpty()){
            String endTimeText = "End Time: " + event.getEndTime();
            endTime.setText(endTimeText);
        }

        // Set max attendees
        if(event.getAttendeeLimit() != 0){
            String maxAttendeesText = "Max Attendees: " + event.getAttendeeLimit();
            maxAttendees.setText(maxAttendeesText);
        }

        // Set qr code image
        if(event.getQrCode() != null) {
            // Generate qr code
            Bitmap qrCodeBitmap = GenerateQRCode.generateQR(event.getQrCode());
            qrCode.setImageBitmap(qrCodeBitmap);
        }

        // Attendee check-in list button
        btnCheckInList.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("Event", event);    // Store event data

            OrganizerCheckInListFragment checkInList = new OrganizerCheckInListFragment();
            checkInList.setArguments((bundle));        // Pass data to Check-In list fragment

            // Navigate to check-in list page
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, checkInList)
                        .addToBackStack(null)         // Optional: Add transaction to back stack
                        .commit();
            }
        });

        // Attendee sign-up list button
        btnSignupList.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("Event", event);    // Store event data

            OrganizerSignUpListFragment signUpList = new OrganizerSignUpListFragment();
            signUpList.setArguments((bundle));           // Pass data to Sign-up list fragment

            // Navigate to sign-up list page
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, signUpList)
                        .addToBackStack(null)      // Optional: Add transaction to back stack
                        .commit();
            }
        });

        // Event notifications button
        // Slightly altered code snippet from AttendeeEventDetailFragment.java, author: Aryan
        btnNotification.setOnClickListener(v -> {
            NotificationListFragment notificationList = new NotificationListFragment();
            Bundle bundle = new Bundle();

            bundle.putSerializable("event_key", event.getId()); // Store event key
            bundle.putSerializable("event", event);             // Store event data

            notificationList.setArguments(bundle);              // Pass data to notification list fragment

            // Navigate to notification list page
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, notificationList)
                        .addToBackStack(null)                 // Optional: Add transaction to back stack
                        .commit();
            }
        });

        // Close button going back previous screen
        btnClose.setOnClickListener(v -> {
            // Check if fragment is added to an activity and if activity has a FragmentManager
            if (isAdded() && getActivity() != null) {
                getActivity().onBackPressed();
            }
        });
        
        return view;

    }
}