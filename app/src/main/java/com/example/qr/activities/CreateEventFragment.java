package com.example.qr.activities;

import static com.example.qr.utils.GenericUtils.getLocationFromAddress;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.qr.R;
import com.example.qr.models.Event;
import com.example.qr.utils.FirebaseUtil;
import com.example.qr.utils.ImagePickerUtil;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.type.LatLng;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

public class CreateEventFragment extends Fragment {
    private ImageView eventPoster;
    private EditText eventTitle, eventLocation;
    private Button btnUseExistingQr, btnGenerateQr, btnCancel;

    private ImagePickerUtil image;
    private CollectionReference eventsRef;

    public CreateEventFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_event, container, false);
        super.onCreate(savedInstanceState);

        // Initialize views
        eventPoster = view.findViewById(R.id.ivEventPoster);
        eventTitle = view.findViewById(R.id.eventTitle);
        eventLocation = view.findViewById(R.id.eventLocation);

        btnUseExistingQr = view.findViewById(R.id.btnUseExistingQr);
        btnGenerateQr = view.findViewById(R.id.btnGenerateQr);
        btnCancel = view.findViewById(R.id.btnCancel);

        // buttons
        btnUseExistingQr.setOnClickListener(v -> {
            // if fields are empty
            if (eventTitle.getText().toString().isEmpty() || eventLocation.getText().toString().isEmpty()) {
                Toast.makeText(getContext(), "Event title or location cannot be empty",
                                Toast.LENGTH_SHORT).show();
                return;
            }
            // convert geolocation to string
            String locationString = eventLocation.getText().toString();
            // check location from GPT given method below
            checkLocationValidity(locationString, isValid -> {
                if (isValid) {
                    // Proceed only if location is valid
                    Bundle args = new Bundle();
                    args.putString("name", eventTitle.getText().toString());
                    args.putString("location", eventLocation.getText().toString());

                    ReuseQrCodeFragment reuseQrCodeFragment = new ReuseQrCodeFragment();
                    reuseQrCodeFragment.setArguments(args);
                    // if item in buffer then do transaction to next screen
                    if (getActivity() != null) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, reuseQrCodeFragment)
                                .addToBackStack(null)  // Optional: Add transaction to back stack
                                .commit();
                    }
                } else {
                    // is not valid location post toast
                    Toast.makeText(getContext(), "Invalid location", Toast.LENGTH_SHORT).show();
                }
            });
        });

        btnGenerateQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if textviews are empty
                if (eventTitle.getText().toString().isEmpty() || eventLocation.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(), "Event title or location cannot be empty",
                                    Toast.LENGTH_SHORT).show();
                    return;
                }
                // convert location to string
                String locationString = eventLocation.getText().toString();
                // if location is  valid location and string
                checkLocationValidity(locationString, isValid -> {
                    if (isValid) {
                        // if valid get the location
                        LatLng location = getLocation();
                        if (location != null) {
                            // if location exists
                            String eventId = "event" + System.currentTimeMillis();
                            FirebaseUtil.addEvent(new Event(eventId, eventTitle.getText().toString(), "", "",
                                                    new Date(), new GeoPoint(location.getLatitude(),
                                                    location.getLongitude()), eventId, "", 0),
                                    documentReference -> {
                                        // GPT given code to switch back to organizer screen
                                        switchToOrganizerFragment();
                                    }, e -> {
                                        // else toast that it failed to create
                                        Toast.makeText(getContext(), "Failed to create event",
                                                        Toast.LENGTH_SHORT).show();
                                    });
                        } else {
                            Toast.makeText(getContext(), "Invalid location", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Invalid location", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


        // citation: OpenAI, ChatGPT 4, 2024: How do I click a button to change fragments
        // in android studio
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a new instance of OrganizerFragment
                OrganizerFragment organizerFragment = new OrganizerFragment();

                // Perform the fragment transaction
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager(); // Use getFragmentManager() in a Fragment
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                // Replace the current fragment with OrganizerFragment. Assume R.id.fragment_container is the ID of your FrameLayout
                fragmentTransaction.replace(R.id.fragment_container, organizerFragment);
                // fragmentTransaction.addToBackStack(null); // Optional: Add this transaction to the back stack
                fragmentTransaction.commit(); // Commit the transaction
            }
        });
        // end citation

        // Add listeners or any additional initialization for other views as needed
        return view;
    }
    // citation: OpenAI, ChatGPT 4, 2024: how do I check if a user input is a valid location
    // in android studio
    private void checkLocationValidity(String locationString, Consumer<Boolean> callback) {
        new Thread(() -> {
            Geocoder geocoder = new Geocoder(getContext());
            try {
                List<Address> addresses = geocoder.getFromLocationName(locationString, 1);
                boolean isValid = addresses != null && !addresses.isEmpty();
                getActivity().runOnUiThread(() -> callback.accept(isValid));
            } catch (IOException e) {
                e.printStackTrace();
                getActivity().runOnUiThread(() -> callback.accept(false));
            }
        }).start();
    }

    private void switchToOrganizerFragment() {
        OrganizerFragment organizerFragment = new OrganizerFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, organizerFragment);
        fragmentTransaction.commit();
    }
    // end of citation
    private LatLng getLocation() {
        return getLocationFromAddress( getActivity()  ,eventLocation.getText().toString());

    }
}
