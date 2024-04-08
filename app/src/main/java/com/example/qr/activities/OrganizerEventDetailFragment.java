package com.example.qr.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.qr.R;
import com.example.qr.models.CheckIn;
import com.example.qr.models.Event;
import com.example.qr.models.SignUp;
import com.example.qr.utils.FirebaseUtil;
import com.example.qr.utils.GenerateQRCode;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * OrganizerEventDetailFragment displays detailed information about an event, including a map of the event location.
 */
public class OrganizerEventDetailFragment extends Fragment implements OnMapReadyCallback {

    public Event event;
    private GoogleMap mMap;


    /**
     *
     * Organizer event detail fragment
     *
     * @return public
     */
    public OrganizerEventDetailFragment() {

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
        Button btnSendNotificaiton = view.findViewById(R.id.btnSendNotification);

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

        // Set start time for non-empty event
        if(!event.getStartTime().isEmpty()){
            String startTimeText = "Start Time: " + event.getStartTime();
            startTime.setText(startTimeText);
        }

        // Set end time for non-empty event
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

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
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
        btnSendNotificaiton.setOnClickListener(v -> {
            OrganizerSendCustomNotification organizerSendCustomNotification = new OrganizerSendCustomNotification();
            Bundle bundle = new Bundle();

            bundle.putSerializable("event", event);             // Store event data
            organizerSendCustomNotification.setArguments(bundle);              // Pass data to notification list fragment
            // Navigate to notification list page
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, organizerSendCustomNotification)
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

        Button btnShareEvent = view.findViewById(R.id.btnShareEvent);
        Button btnShareQR = view.findViewById(R.id.btnShareQR);

        btnShareEvent.setOnClickListener(v -> {

            if(event.getQrpCode().isEmpty()) {
                return;
            }

            Bitmap qrCodeBitmap = GenerateQRCode.generateQR(event.getQrpCode());
            String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), qrCodeBitmap, "QR Promo Code", null);
            Uri uri = Uri.parse(path);

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/jpeg");
            startActivity(Intent.createChooser(shareIntent, "Share QR Promo Code via"));
        });

        btnShareQR.setOnClickListener(v -> {

            if(event.getQrCode().isEmpty()) {
                return;
            }

            Bitmap qrCodeBitmap = GenerateQRCode.generateQR(event.getQrCode());
            String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), qrCodeBitmap, "QR Code", null);
            Uri uri = Uri.parse(path);

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/jpeg");
            startActivity(Intent.createChooser(shareIntent, "Share QR Code via"));
        });

        return view;

    }



    /**
     *
     * Add check in markers
     *
     */
    private void addCheckInMarkers() {

        // Query all check-ins for the event
        FirebaseUtil.fetchCollection("CheckIn", CheckIn.class, new FirebaseUtil.OnCollectionFetchedListener<CheckIn>() {
            @Override

/**
 *
 * On collection fetched
 *
 * @param checkInList  the check in list.
 */
            public void onCollectionFetched(List<CheckIn> checkInList) {

                for (CheckIn checkIn : checkInList) {
                    // Check if the check-in is for the current event
                    if (checkIn.getEventId().equals(event.getId())) {
                        // Add a marker for each check-in
                        //check if location exists
                        if(checkIn.getLocation() != null) {
                            LatLng location = new LatLng(checkIn.getLocation().getLatitude(), checkIn.getLocation().getLongitude());
                            mMap.addMarker(new MarkerOptions().position(location).title(checkIn.getUserId()));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 20));
                        }
                    }
                }
            }

            @Override

/**
 *
 * On error
 *
 * @param e  the e.
 */
            public void onError(Exception e) {

                Log.e("OrganizerEventDetailFragment", "Error fetching check-ins", e);
            }
        });
    }

    @Override

/**
 *
 * On map ready
 *
 * @param googleMap  the google map.
 */
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        addCheckInMarkers();
    }

}
