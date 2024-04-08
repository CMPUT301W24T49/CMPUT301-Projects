package com.example.qr.activities;

import static com.example.qr.activities.MainActivity.androidId;

import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.qr.R;
import com.example.qr.models.Event;
import com.example.qr.models.SignUp;
import com.example.qr.utils.FirebaseUtil;
import com.example.qr.utils.GenerateQRCode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AttendeeEventDetailFragment extends Fragment {

    public Event event;

    public AttendeeEventDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_attendee_event_detail, container, false);
        event = (Event) getArguments().getSerializable("Event");
        boolean noSignUp = (boolean) getArguments().getSerializable("NoSignUp");

        // Intialize all the views

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
        Button btnSignUp = view.findViewById(R.id.btnSignUp);
        Button btnNotification = view.findViewById(R.id.btnNotification);

        Button btnClose = view.findViewById(R.id.btn_close_event_list);
        // Close button going back previous screen
        btnClose.setOnClickListener(v -> {
            // Check if fragment is added to an activity and if activity has a FragmentManager
            if (isAdded() && getActivity() != null) {
                getActivity().onBackPressed();
            }
        });

        if(noSignUp){
            btnSignUp.setVisibility(View.GONE);
            btnSignUp.setClickable(false);
        }
        else{
            btnNotification.setVisibility(View.GONE);
            btnSignUp.setClickable(false);
        }


        //fetch data from event object and set it to the views

        if(event.getEventPoster() != ""){
            Glide.with(getContext()).load(event.getEventPoster()).into(profile);
        }

        if(event.getId() != null){
            String titleText = "Title: " + event.getTitle();
            title.setText(titleText);
        }

        if(event.getDescription() != null){
            String descText = "Description: " + event.getDescription();
            description.setText(descText);
        }

        //convert geopoint into an actual address
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(event.getLocation().getLatitude(), event.getLocation().getLongitude(), 1);
            if(addresses != null && !addresses.isEmpty()) {
                String address = addresses.get(0).getAddressLine(0);
                String locationText = "Location: " + address;
                location.setText(locationText);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        if(event.getStartDate() != null){
            String startDateText = "Start Date: " + android.text.format.DateFormat.format("dd-MM-yyyy", event.getStartDate());
            startDate.setText(startDateText);
        }

        if(event.getEndDate() != null){
            String endDateText = "End Date: " + android.text.format.DateFormat.format("dd-MM-yyyy", event.getEndDate());
            endDate.setText(endDateText);
        }

        if(event.getStartTime() != null &&  !event.getStartTime().isEmpty()){
            String startTimeText = "Start Time: " + event.getStartTime();
            startTime.setText(startTimeText);
        }

        if(event.getEndTime() != null && !event.getEndTime().isEmpty()){
            String endTimeText = "End Time: " + event.getEndTime();
            endTime.setText(endTimeText);
        }

        if(event.getAttendeeLimit() != 0){
            String maxAttendeesText = "Max Attendees: " + event.getAttendeeLimit();
            maxAttendees.setText(maxAttendeesText);
        }

        //set the qr code image
        if(event.getQrCode() != null) {
            //generate qr code

            Bitmap qrCodeBitmap = GenerateQRCode.generateQR(event.getQrCode());
            qrCode.setImageBitmap(qrCodeBitmap);

        }

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationListFragment notificationListFragment = new NotificationListFragment();
                Bundle args = new Bundle();
                args.putSerializable("event_key", event.getId());
                args.putSerializable("event", event);
                notificationListFragment.setArguments(args);
                if (getActivity() != null) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, notificationListFragment)
                            .addToBackStack(null)
                            .commit();
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(event.getAttendeeCount() == null){
                    event.setAttendeeCount(0);
                }

                //check if events has empty spots
                if(event.getAttendeeCount() >= event.getAttendeeLimit()){
                    Toast.makeText(getContext(), "Event is full!", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUtil.fetchCollection("SignUp", SignUp.class, new FirebaseUtil.OnCollectionFetchedListener<SignUp>() {
                    @Override
                    public void onCollectionFetched(List<SignUp> SignUps) {
                        //filter the signups to get the signups only for this event id
                        List<SignUp> eventSignUps = new ArrayList<>();

                        for(SignUp signUp : SignUps){
                            if(signUp.getEventId() == null){
                                continue;
                            }
                            if(signUp.getEventId().equals(event.getId())){
                                //add the signups to the list
                                eventSignUps.add(signUp);
                            }
                        }

                        boolean isSignedUp = false;

                        for(SignUp signUp : eventSignUps){

                            if(signUp.getUserId() == null) {
                                continue;
                            }

                            if(signUp.getUserId().equals(androidId)){
                                    isSignedUp = true;
                                    break;
                            }

                        }

                        if(isSignedUp) {
                            //show a message that the user has already signed up for this event
                            Toast.makeText(getContext(), "Already Signed Up!", Toast.LENGTH_SHORT).show();
                        }else {
                            //sign up the user for the event
                            //get current time
                            Date date = new java.util.Date();
                            SignUp signUp = new SignUp(event.getId(), androidId, date, event.getLocation());
                            FirebaseUtil.addSignUp(signUp, aVoid -> {
                                Toast.makeText(getContext(), "Signed Up Successfully!", Toast.LENGTH_SHORT).show();
                                event.setAttendeeCount(event.getAttendeeCount() + 1);
                                FirebaseUtil.addEvent(event, aVoid1 -> {
                                    Log.d("AttendeeEventDetailFragment", "Event updated successfully");
                                }, e -> {
                                    Log.d("AttendeeEventDetailFragment", "Failed to update event");
                                });
                            }, e -> {
                                Toast.makeText(getContext(), "Failed to Sign Up!", Toast.LENGTH_SHORT).show();
                            });
                        }

                    }

                    @Override
                    public void onError(Exception e) {
                    }


                });

            }
        });

        return view;
    }



}
